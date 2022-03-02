package com.bardxhong.crypto.shared

/**
 * Be used to intentionally throw in test and check with @Test(expected = FakeException::class)
 */
object FakeException : Exception("fake")