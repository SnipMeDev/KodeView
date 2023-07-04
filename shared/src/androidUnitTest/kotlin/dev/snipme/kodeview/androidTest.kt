package dev.snipme.kodeview

import dev.snipme.highlights.Highlights
import org.junit.Assert.assertTrue
import org.junit.Test

class AndroidGreetingTest {

    @Test
    fun testExample() {
        assertTrue("Check Android is mentioned", Highlights.default().getHighlights().isEmpty())
    }
}