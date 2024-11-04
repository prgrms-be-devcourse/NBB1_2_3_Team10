package org.tenten.bittakotlin.like.exception

import lombok.AllArgsConstructor
import lombok.Getter

@Getter
@AllArgsConstructor
class LikeTaskException(
    override val message: String,
    val code: Int
) : RuntimeException(message)