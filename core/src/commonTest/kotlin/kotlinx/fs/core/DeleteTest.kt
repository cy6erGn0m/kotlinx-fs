package kotlinx.fs.core

import kotlin.test.*

class DeleteTest : TestBase() {

    @Test
    fun deleteFile() {
        val file = testFile("delete-file").createFile()
        assertTrue(file.exists())
        file.delete()
        assertFalse(file.exists())

    }

    @Test
    fun deleteNonEmptyDirectory() {
        val directory = testFile("delete-directory").createDirectory()
        val file = (directory + "nested-file.txt").createFile()

        assertTrue(directory.exists())
        assertTrue(file.exists())

        assertFailsWith<IOException> { directory.delete() }

        directory.deleteDirectory()
        assertFalse(directory.exists())
        assertFalse(file.exists())
    }

    @Test
    fun deleteNonExisting() {
        val file = testFile("non-existing")
        assertFalse(file.exists())
        assertFalse(file.deleteIfExists())
        assertFailsWith<IOException> { file.delete() }
    }
}
