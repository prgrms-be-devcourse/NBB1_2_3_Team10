package org.tenten.bittakotlin

import org.junit.jupiter.api.Test

/*
    @SpringBootTest는 사용하지 말아 주시길 바랍니다.
    테스트할 때 모든 데이터를 불러오므로 에러가 발생할 수 있습니다.
    현재 S3 액세스 키, 시크릿 키 등의 유출되면 안 되는 데이터를 요구하는데,
    test/resources/application.properties에 추가하면 안됩니다.

    컨트롤러는 @WebMvcTest, 레포지토리는 @DataJpaTest,
    서비스는 @ExtendWith(MockitoExtension::class.java)로 대체할 수 있습니다.
 */
class BittaKotlinApplicationKtTest {

    @Test
    fun main() {
    }
}