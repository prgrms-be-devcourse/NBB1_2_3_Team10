package org.tenten.bittakotlin.jobpost.exception

import lombok.AllArgsConstructor
import lombok.Getter

@Getter
@AllArgsConstructor
class JobPostTaskException (
    override val message: String,
    val code: Int
) : RuntimeException(message)
