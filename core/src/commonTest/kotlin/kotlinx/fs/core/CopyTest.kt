package kotlinx.fs.core

import kotlinx.io.errors.*
import kotlin.test.*

class CopyTest : TestBase() {

    @Test
    fun testCopyFile() {
        val expectedContent = ByteArray(5) { it.toByte() }
        val file = testFile("copy-file").createFile()
        file.writeBytes(expectedContent)

        val target = testFile("target-file")
        file.copyTo(target)

        assertTrue(expectedContent.contentEquals(target.readAllBytes()))
        assertTrue(expectedContent.contentEquals(file.readAllBytes()))
        assertTrue(file.exists())
    }

    @Test
    fun testCopyDirectory() {
        val directory = testDirectory("copy-directory").createDirectory()
        val target = testDirectory("target-directory")
        assertFalse(target.exists())

        directory.copyTo(target)
        assertTrue(directory.exists())
        assertTrue(target.exists())
    }

    @Test
    fun testCopyDirectoryWithContent() {
        val expectedContent = ByteArray(42) { it.toByte() }
        val directory = testDirectory("copy-directory-with-content").createDirectory()
        val file = Path(directory, "content.txt").createFile()
        file.writeBytes(expectedContent)

        val target = testDirectory("target-directory")
        val targetFile =  Paths.getPath(target, "content.txt")
        assertFalse(target.exists())
        assertFalse(targetFile.exists())

        directory.copyTo(target)


        // Content is not copied, but directory is
        assertTrue(target.exists())
        assertFalse(targetFile.exists())
    }

    @Test
    fun testCopyNothing() {
        val file = testFile("nothing")
        val target = testFile("nothing-target")
        assertFailsWith<IOException> { file.copyTo(target)  }
    }

    @Test
    fun testCopyToExistingFile() {
        val file = testFile("file").createFile()
        val target = testFile("existing-file").createFile()
        assertFailsWith<IOException> { file.copyTo(target)  }
    }
}
