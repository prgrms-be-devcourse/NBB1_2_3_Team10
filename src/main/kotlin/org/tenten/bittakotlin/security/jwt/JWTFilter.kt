import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import org.tenten.bittakotlin.member.entity.Member
import org.tenten.bittakotlin.member.repository.MemberRepository
import org.tenten.bittakotlin.security.dto.CustomUserDetails
import org.tenten.bittakotlin.security.jwt.JWTUtil
import java.io.IOException
import java.io.PrintWriter

class JWTFilter(private val jwtUtil: JWTUtil) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        // 헤더에서 access키에 담긴 토큰을 꺼냄
        val accessToken = request.getHeader("access")

        // 토큰이 없다면 다음 필터로 넘김
        if (accessToken == null) {
            filterChain.doFilter(request, response)
            return
        }

        // 토큰 만료 여부 확인, 만료시 다음 필터로 넘기지 않음
        try {
            jwtUtil.isExpired(accessToken)
        } catch (e: ExpiredJwtException) {
            // response body
            val writer: PrintWriter = response.writer
            writer.print("access token expired")

            // response status code
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            return
        }

        // 토큰이 access인지 확인 (발급시 페이로드에 명시)
        val category = jwtUtil.getCategory(accessToken)

        if (category != "access") {
            // response body
            val writer: PrintWriter = response.writer
            writer.print("invalid access token")

            // response status code
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            return
        }

        // username, role 값을 획득
        val username = jwtUtil.getUsername(accessToken)
        val role = jwtUtil.getRole(accessToken)

        val member = Member().apply {
            this.username = username
            this.role = role
        }

        val customUserDetails = CustomUserDetails(member)

        val authToken = UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.authorities)
        SecurityContextHolder.getContext().authentication = authToken

        filterChain.doFilter(request, response)
    }
}