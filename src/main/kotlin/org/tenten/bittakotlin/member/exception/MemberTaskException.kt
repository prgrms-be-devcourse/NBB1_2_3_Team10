package org.tenten.bittakotlin.member.exception

import lombok.AllArgsConstructor
import lombok.Getter

@Getter
@AllArgsConstructor
class MemberTaskException (
    override val message: String,
    val code: Int
) : RuntimeException(message)