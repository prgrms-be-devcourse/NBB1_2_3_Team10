package org.tenten.bittakotlin.scout.exception

import lombok.AllArgsConstructor
import lombok.Getter

@AllArgsConstructor
@Getter
class ScoutTaskException : RuntimeException() {
    private val code = 0
    override val message: String? = null
}