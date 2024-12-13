package com.example.androidcookbook

import com.example.androidcookbook.ui.common.utils.apiDateFormatter
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate
import java.time.OffsetDateTime
import java.time.ZoneOffset

class DateTimeFormatterTest {

    @Test
    fun testApiDateFormatter_parseValidInput() {
        val input = "2024-12-05T16:34:54.801Z"
        val expectedDateTime = OffsetDateTime.of(2024, 12, 5, 16, 34, 54, 801000000, ZoneOffset.UTC)

        val parsedDateTime = LocalDate.parse(input, apiDateFormatter)

        assertEquals(expectedDateTime.toLocalDate(), parsedDateTime)
    }

    @Test(expected = java.time.format.DateTimeParseException::class)
    fun testApiDateFormatter_parseInvalidInput_throwsException() {
        val invalidInput = "2024-12-05 16:34:54.801Z" // Invalid format

        OffsetDateTime.parse(invalidInput, apiDateFormatter)
    }

    @Test(expected = java.time.format.DateTimeParseException::class)
    fun testApiDateFormatter_parseInvalidInputNoMillis_throwsException() {
        val invalidInput = "2024-12-05 16:34:54Z" // Invalid format

        OffsetDateTime.parse(invalidInput, apiDateFormatter)
    }

    @Test(expected = java.time.format.DateTimeParseException::class)
    fun testApiDateFormatter_emptyString_throwsException() {
        val invalidInput = "" // Invalid format

        OffsetDateTime.parse(invalidInput, apiDateFormatter)
    }
}