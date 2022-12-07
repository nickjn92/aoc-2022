package day7

data class File(
    val name: String,
    val size: Int
)

data class Directory(
    val parent: Directory?,
    val name: String,
    val files: MutableList<File> = mutableListOf(),
    val dirs: MutableList<Directory> = mutableListOf()
)

fun Directory.size(): Int {
    return files.sumOf { file -> file.size } + dirs.sumOf { dir -> dir.size() }
}

fun parseDirectories(input: List<String>): Map<String, Directory> {
    val rootDirectory = Directory(null, "/")

    val directories = mutableMapOf(
        rootDirectory.name to rootDirectory
    )
    var currentDirectory = rootDirectory

    input.forEach { line ->
        when {
            line.startsWith("$ cd ..") -> {
                currentDirectory = currentDirectory.parent ?: rootDirectory
            }
            line.startsWith("$ cd ") -> {
                val dirName = line.substring("$ cd ".length)
                if (dirName == "/") {
                    currentDirectory = rootDirectory
                } else {
                    val newDir = Directory(currentDirectory, dirName)
                    directories["${newDir.parent?.name}/${newDir.name}"] = newDir
                    currentDirectory.dirs.add(newDir)
                    currentDirectory = newDir
                }
            }
            line.matches("\\d+ \\w+[.\\w+]*".toRegex()) -> {
                val (size, name) = line.split(" ")
                currentDirectory.files.add(File(name, size.toInt()))
            }
        }
    }
    return directories
}
