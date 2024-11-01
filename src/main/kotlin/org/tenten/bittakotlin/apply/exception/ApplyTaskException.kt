package org.tenten.bittakotlin.apply.exception

import lombok.AllArgsConstructor
import lombok.Getter

@Getter
@AllArgsConstructor
class ApplyTaskException(
    override val message: String,
    val code: Int
) : RuntimeException(message)


