package sealwing.elephant.desktop.converter

import sealwing.elephant.desktop.core.*

// TO STRING
/*
fun fromDealToString(deal: Deal): String {
    val str = StringBuilder("Заказ")
    if (!deal.id.equals("N/A")) str.append(" №${deal.id}")
    if (!deal.address.equals("N/A")) str.append(" по адресу ${deal.address}")
    str.append("\nКонструкции:")
    for (struct in deal.constructionList) {
        str.append("\nКонструкция ${getRussianTitle(struct.type)} размером ${struct.height}x${struct.width} в количестве ${struct.amount}:")
        for ((type, length, profAmount) in struct.profilesList) str.append("\n\t${getRussianTitle(type)} длиной ${length} - $profAmount шт.")
    }
    if (deal.supplementList.size > 0) {
        str.append("\nДополнения:")
        for ((type, amount) in deal.supplementList) str.append("\n\t${getRussianTitle(type)} - $amount шт.")
    }
    return str.toString()
}*/

fun dealHeaderToString(deal: Deal): String =
        if (deal.address == "N/A" && deal.id == "N/A") "Заказ не определён"
        else {
            "Заказ " +
                    if (deal.id != "N/A") {
                        "№${deal.id} "
                    } else {
                        ""
                    } +
                    if (deal.address != "N/A") {
                        "по адресу ${deal.address}"
                    } else {
                        ""
                    }
        }

fun fromStructToString(deal: Deal, id: Int = -1): String {
    val stack = if (id > -1) deal.constructionList[id] else deal.constructionList[deal.constructionList.lastIndex]
    val str = StringBuilder("${getRussianTitle(stack.type)} ${stack.width}x${stack.height}, ${stack.amount} шт.:")
    for ((type, length, amount) in stack.profilesList) {
        str.append("\n\t${getRussianTitle(type)} - $length мм., ${amount * stack.amount} шт.")
    }
    str.append("\n\tЗаполнение ${stack.fitWidth}x${stack.fitHeight} мм.")
    return str.toString()
}

fun supplementToString(deal: Deal): String =
        if (deal.supplementList.size > 0) {
            val str = StringBuilder()
            for ((key, value) in deal.supplementList) str.append("${getRussianTitle(key)} - ${value}шт.\n")
            str.toString()
        } else {
            "Нет дополнений"
        }

// RUSSIAN TITLES

fun getRussianTitle(type: ProfileType): String =
        when (type) {
            ProfileType.INNER -> "Центральная створка"
            ProfileType.OUTER -> "Внешняя створка"
            ProfileType.HORIZONTAL -> "Горизонтальная створка"
            ProfileType.SOLID_FRAME -> "Рама глухаря"
            ProfileType.SOLID_FIT -> "Штапик глухаря"
            ProfileType.HORIZONTAL_FRAME -> "Горизонтальная рама"
            ProfileType.VERTICAL_FRAME -> "Боковая рама"
            ProfileType.STULP -> "Штульп"
        }

fun getRussianTitle(type: ConstructionType) =
        when (type) {
            ConstructionType.DOUBLE -> "2 створки"
            ConstructionType.TRIPLE -> "3 створки"
            ConstructionType.QUARTET -> "4 створки"
            ConstructionType.SOLID -> "Глухарь"
        }

fun getRussianTitle(type: SupplementType) =
        when (type) {
            SupplementType.FRAME_SEPARATOR -> "Соединение для рам"
            SupplementType.CORNER_CONNECTOR_60 -> "Угловое соединение 60х60"
            SupplementType.CORNER_CONNECTOR_40 -> "Угловое соединение 40х60"
        }