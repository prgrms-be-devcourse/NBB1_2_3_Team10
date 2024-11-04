package com.prgrms2.java.bitta.feed.exception

import org.tenten.bittakotlin.feed.constant.FeedError

class FeedException(
    val code: Int,

    override val message: String
) : RuntimeException(message) {
    constructor(feedError: FeedError): this(
        code = feedError.code,
        message = feedError.message
    )
}