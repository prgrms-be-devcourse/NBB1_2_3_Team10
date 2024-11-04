package com.prgrms2.java.bitta.feed.exception

class FeedTaskException(
    val code: Int,
    override val message: String
) : RuntimeException(message)