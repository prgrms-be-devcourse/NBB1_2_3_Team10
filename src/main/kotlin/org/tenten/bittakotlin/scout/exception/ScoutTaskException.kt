package org.tenten.bittakotlin.scout.exception

class ScoutTaskException(
    val code: Int,
    override val message: String
) : RuntimeException(message)