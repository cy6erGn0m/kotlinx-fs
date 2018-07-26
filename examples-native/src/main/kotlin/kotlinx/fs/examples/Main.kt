package kotlinx.fs.examples


fun main(args: Array<String>) {
    // Test commit
    Ls.execute(args.lastOrNull() ?: ".", emptyMap())
}
