package com.example.tailor.util

import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class ValidatorTest {

    private lateinit var validator: Validator

    @Before
    fun setUp(){
        validator = Validator()
    }


    @Test
    fun emailIsValidWithInvalidEmailThenReturnFalseValue(){
        val validation = validator.emailIsValid("test@.com")
        assertEquals(false,validation)
    }

    @Test
    fun emailIsValidWithValidEmailThenReturnTrueValue(){
        val validation = validator.emailIsValid("test_123@gmail.com")
        assertEquals(true, validation)

    }


    @Test
    fun fullNameIsValidWithInvalidNameThenReturnFalseValue() {
        val validation = validator.fullNameIsValid("Example234")
        assertEquals(false,validation)
    }

    @Test
    fun fullNameIsInvalidWithValidNameThenReturnTrueValue(){
        val validation = validator.fullNameIsValid("Example Example")
        assertEquals(true,validation)
    }

    @Test
    fun passwordISValidWithInvalidPasswordThenReturnFalseValue() {
        val validation = validator.passwordISValid("example12@12")
        assertEquals(false,validation)
    }



    @Test
    fun passwordIsValidWithValidPasswordThenReturnTrueValue(){
        val validation = validator.passwordISValid("Example@123")
        assertEquals(true,validation)

    }
}