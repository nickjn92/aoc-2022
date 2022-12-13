package day13

import day13.Packet.IntPacket
import day13.Packet.ListPacket

sealed class Packet : Comparable<Packet> {
    data class IntPacket(
        val value: Int
    ) : Packet() {
        override fun compareTo(other: Packet): Int {
            return when (other) {
                is IntPacket -> {
                    println("Comparing $this to $other")
                    this.value compareTo other.value
                }
                is ListPacket -> ListPacket(mutableListOf(this)) compareTo other
            }
        }

        override fun toString(): String {
            return value.toString()
        }
    }

    data class ListPacket(
        val packets: List<Packet>
    ) : Packet() {
        override fun compareTo(other: Packet): Int {
            return when (other) {
                is IntPacket -> this compareTo ListPacket(mutableListOf(other))
                is ListPacket -> {
                    println("Comparing ${this.packets} to ${other.packets}")
                    val i = this.packets.iterator()
                    val j = other.packets.iterator()
                    while (i.hasNext() && j.hasNext()) {
                        val comparison = i.next() compareTo j.next()
                        if (comparison != 0) {
                            return comparison
                        }
                    }
                    i.hasNext() compareTo j.hasNext()
                }
            }
        }

        override fun toString(): String {
            return packets.toString()
        }
    }
}

fun List<String>.toPackets(): List<Packet> {
    return mapNotNull { packetStr -> if (packetStr.isBlank()) null else packetStr.toPacket() }
}

fun String.toPacket(): Packet {
    val (index, packet) = parser.invoke(IndexedValue(0, this))
    require(index == length)
    return packet
}

val parser = DeepRecursiveFunction<IndexedValue<String>, IndexedValue<Packet>> { (startIndex, string) ->
    if (string[startIndex] == '[') {
        var index = startIndex + 1
        val list = buildList {
            while (string[index] != ']') {
                val (endIndex, value) = callRecursive(IndexedValue(index, string))
                add(value)
                index = if (string[endIndex] == ',') endIndex + 1 else endIndex
            }
        }
        IndexedValue(index + 1, ListPacket(list))
    } else {
        var index = startIndex + 1
        while (index < string.length && string[index] in '0'..'9') index++
        IndexedValue(index, IntPacket(string.substring(startIndex, index).toInt()))
    }
}
