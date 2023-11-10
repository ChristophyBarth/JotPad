package capps.jotpad.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import capps.jotpad.others.Object
import capps.jotpad.others.getOrAwaitValue
import com.nhaarman.mockitokotlin2.mock
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EditFragVMTest {

    // To handle background threading with LiveData
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: EditFragVM

    @Before
    fun setup() {
        viewModel = EditFragVM(false, null, mock())
    }

    @Test
    fun `test colorReturned when colorString is not empty`() {
        // Given
        val randomColorString = Object.getRandomColorCode()

        // When
        viewModel.colorReturned(randomColorString)

        // Then
        val value = viewModel.colorCode.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled(), not(""))
    }

    @Test //This test is expected to fail because randomColorString is empty.
    fun `test colorReturned when colorString is empty`() {
        // Given
        val randomColorString = ""

        // When
        viewModel.colorReturned(randomColorString)

        // Then
        val value = viewModel.colorCode.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled(), not(""))
    }
}