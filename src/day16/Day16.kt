package day16

import readInput

fun main() {
    fun part1(input: String): Int {
        val packet = input.hexToPacket()
        val subPackets = packet.getSubPackets()

        return (subPackets + listOf(packet)).sumOf { it.version }
    }

    fun part2(input: String): Long {
        return input.hexToPacket().getPacketValue()
    }

    // test if implementation meets criteria from the description, like:
    check(part1("8A004A801A8002F478") == 16)
    check(part1("620080001611562C8802118E34") == 12)
    check(part1("C0015000016115A2E0802F182340") == 23)
    check(part1("A0016C880162017C3686B18A3D4780") == 31)

    check(part2("C200B40A82") == 3L)
    check(part2("04005AC33890") == 54L)
    check(part2("880086C3E88112") == 7L)
    check(part2("CE00C43D881120") == 9L)
    check(part2("D8005AC2A8F0") == 1L)
    check(part2("F600BC2D8F") == 0L)
    check(part2("9C005AC2F8F0") == 0L)
    check(part2("9C0141080250320F1802104A08") == 1L)

    val input = readInput("day16/input")
    println(part1(input.first()))
    println(part2(input.first()))
}

sealed class Packet(
    protected val bitString: String
) {

    val version = bitString.substring(0, 3).toInt(2)
    protected val type = bitString.substring(3, 6).toInt(2)

    protected var length = 0

    abstract fun getSubPackets(): List<Packet>

    abstract fun getPacketValue(): Long

    class LiteralPacket(bitString: String) : Packet(bitString) {

        val pValue = literalValue()

        override fun getSubPackets(): List<Packet> {
            return emptyList()
        }

        override fun getPacketValue(): Long {
            return pValue
        }

        private fun literalValue(): Long {
            val end = (
                bitString.substring(6, bitString.length).windowed(1, 5)
                    .mapIndexedNotNull { index, s -> if (s == "0") index else null }
                    .first() + 1
                ) * 5 + 6

            length = end

            return bitString.substring(6, end)
                .windowed(5, 5)
                .joinToString("") { it.substring(1) }
                .toLong(2)
        }
    }

    class OperatorPacket(bitString: String) : Packet(bitString) {

        // If 0, next 15 bits are number that represents total length in bits of the sub packets container by this packet
        // If 1, the next 11 bits are a number that represents the number of sub-packets immediately contained by this packet

        private val lengthType = bitString.substring(6, 7).toInt(2)
        private val directSubPackets = mutableListOf<Packet>()
        override fun getSubPackets(): List<Packet> {
            directSubPackets.clear()
            val subPackets = mutableListOf<Packet>()

            when (lengthType) {
                0 -> {
                    val totalSubpacketsLength = bitString.substring(7, 22).toInt(2)
                    val subPacketsBitString = bitString.substring(22, 22 + totalSubpacketsLength)
                    length = 22 + totalSubpacketsLength
                    var parsedBits = 0
                    while (parsedBits < totalSubpacketsLength) {
                        val packet = subPacketsBitString.substring(parsedBits).parseNextPacket()
                        subPackets.addAll(listOf(packet) + packet.getSubPackets())
                        directSubPackets.add(packet)
                        parsedBits += packet.length
                    }
                }
                else -> {
                    val totalSubpackets = bitString.substring(7, 18).toInt(2)
                    var previousPacketSizes = 0
                    var addedRootPackets = 0
                    while (addedRootPackets < totalSubpackets) {
                        val packet = bitString.substring(18 + previousPacketSizes, bitString.length).parseNextPacket()
                        subPackets.addAll(listOf(packet) + packet.getSubPackets())
                        directSubPackets.add(packet)
                        previousPacketSizes += packet.length
                        addedRootPackets++
                    }
                    length = 18 + previousPacketSizes
                }
            }
            return subPackets
        }

        override fun getPacketValue(): Long {
            getSubPackets()
            val subPacketsValues = directSubPackets.map { it.getPacketValue() }
            return when (type) {
                0 -> subPacketsValues.sum()
                1 -> subPacketsValues.fold(1, Long::times)
                2 -> subPacketsValues.minOf { it }
                3 -> subPacketsValues.maxOf { it }
                5 -> if (directSubPackets[0].getPacketValue() > directSubPackets[1].getPacketValue()) 1L else 0L
                6 -> if (directSubPackets[0].getPacketValue() < directSubPackets[1].getPacketValue()) 1L else 0L
                7 -> if (directSubPackets[0].getPacketValue() == directSubPackets[1].getPacketValue()) 1L else 0L
                else -> error("Should not happen")
            }
        }
    }
}

private fun String.hexToBinary(): String {
    val mappings = mapOf(
        "0" to "0000",
        "1" to "0001",
        "2" to "0010",
        "3" to "0011",
        "4" to "0100",
        "5" to "0101",
        "6" to "0110",
        "7" to "0111",
        "8" to "1000",
        "9" to "1001",
        "A" to "1010",
        "B" to "1011",
        "C" to "1100",
        "D" to "1101",
        "E" to "1110",
        "F" to "1111"
    )
    return this.map { mappings[it.toString()]!! }.joinToString("")
}

private fun String.hexToPacket(): Packet {
    return this.hexToBinary().parseNextPacket()
}

private fun String.parseNextPacket(): Packet {
    return when (this.substring(3, 6).toInt(2)) {
        4 -> Packet.LiteralPacket(this)
        else -> Packet.OperatorPacket(this)
    }
}
