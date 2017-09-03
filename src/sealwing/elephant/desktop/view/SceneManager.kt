package sealwing.elephant.desktop.view

import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import sealwing.elephant.desktop.converter.dealHeaderToString
import sealwing.elephant.desktop.core.Construction
import sealwing.elephant.desktop.core.ConstructionType
import sealwing.elephant.desktop.core.Deal
import sealwing.elephant.desktop.converter.fromStructToString
import sealwing.elephant.desktop.converter.supplementToString

class SceneManager {

    private lateinit var currentDeal: Deal
    private var dealSet = false

    val scene: Scene
        get() {
            addActions()
            toggleButtons()
            wrap()
            val scene = Scene(box, 440.0, 500.0)
            return scene
        }

    private val box = VBox()

    private val nameBox = HBox()
    private val sizeBox = HBox()
    private val constBox = HBox()
    private val controlBox = HBox()

    private val infoTree: TreeView<Label> = TreeView()

    private val type = ToggleGroup()
    private val doubleType = RadioButton("2-х")
    private val tripleType = RadioButton("3-х")
    private val quadroType = RadioButton("4-х")
    private val solidType = RadioButton("Гл.")

    private val constAmount = ComboBox<String>()

    private val dealId = TextField()
    private val address = TextField()

    private val height: TextField = TextField()
    private val width: TextField = TextField()

    private val count = Button("Посчитать")
    private val save = Button("Сохранить")
    private val add = Button(("Добавить"))
    private val clear = Button("Удалить")

    private val rootItem = TreeItem<Label>()
    private val constructItem = TreeItem<Label>(Label("Конструкции:"))
    private val supplementItem = TreeItem<Label>(Label("Сопутствующие материалы:"))
    private val costItem = TreeItem<Label>(Label("Стоимость:"))

    init {
        constAmount.items.addAll("1", "2", "3", "4")
        constAmount.value = constAmount.items[0]
        constAmount.isEditable = false

        nameBox.alignment = Pos.CENTER
        sizeBox.alignment = Pos.CENTER
        constBox.alignment = Pos.CENTER
        controlBox.alignment = Pos.CENTER

        constBox.padding = Insets(5.0, 0.0, 5.0, 0.0)

        constBox.spacing = 10.0
        controlBox.spacing = 5.0

        rootItem.children.addAll(constructItem, supplementItem, costItem)
        rootItem.value = Label("Информация о заказе")
        infoTree.root = rootItem

        dealId.promptText = "Номер заказа"
        address.promptText = "Адрес"
        width.promptText = "Ширина"
        height.promptText = "Высота"
    }


    private fun addActions() {
        count.setOnAction { count() }
        save.setOnAction { save() }
        add.setOnAction { addStruct() }
        clear.setOnAction { deleteStruct() }


        width.setOnKeyTyped { key -> if (!key.character[0].isDigit()) key.consume() }
        height.setOnKeyTyped { key -> if (!key.character[0].isDigit()) key.consume() }
    }

    private fun count() {
        if (haveParams()) {
            val construction = Construction(getType(), width = width.text.toInt(), height = height.text.toInt(), amount = constAmount.value.toInt())
            if (!dealSet) {
                currentDeal = Deal(construction)
                dealSet = true
            } else {
                currentDeal.deleteAll()
                clearLists()
                currentDeal.addConstruction(construction)
            }
            currentDeal.address = if (address.text.isNotEmpty()) address.text else "N/A"
            currentDeal.id = if (dealId.text.isNotEmpty()) dealId.text else "N/A"
            newStruct()
            rootItem.value.text = dealHeaderToString(currentDeal)
        }
    }

    private fun save() {
        val canSave = dealSet && (address.text.isNotEmpty() || dealId.text.isNotEmpty())
        if (canSave) {
            // SAVE
        }
    }

    private fun addStruct() {
        if (haveParams() && dealSet) {
            currentDeal.addConstruction(Construction(getType(), width = width.text.toInt(), height = height.text.toInt(), amount = 1))
            newStruct()
        }
    }

    private fun deleteStruct() {
        if (dealSet && currentDeal.deleteLast()) {
            constructItem.children.removeAt(constructItem.children.lastIndex)
            refreshCost()
        }
    }

    private fun newStruct() {
        constructItem.children.add(TreeItem<Label>(Label(fromStructToString(currentDeal))))
    }

    private fun clearLists() {
        constructItem.children.clear()
        supplementItem.children.clear()
        costItem.children.clear()
    }

    private fun refreshAdditions() {
        if (supplementItem.children.lastIndex < 1) supplementItem.children.add(TreeItem<Label>(Label(supplementToString(currentDeal))))
        else supplementItem.children[0].value.text = supplementToString(currentDeal)
    }

    private fun refreshCost() {

    }

    private fun haveParams(): Boolean = width.text.isNotEmpty() && height.text.isNotEmpty()


    private fun toggleButtons() {
        doubleType.toggleGroup = type
        tripleType.toggleGroup = type
        quadroType.toggleGroup = type
        quadroType.isSelected = true
        solidType.toggleGroup = type
    }

    private fun wrap() {
        nameBox.children.addAll(dealId, address)
        sizeBox.children.addAll(width, height)
        constBox.children.addAll(doubleType, tripleType, quadroType, solidType, constAmount)
        controlBox.children.addAll(count, save, add, clear)
        box.children.addAll(nameBox, sizeBox, constBox, controlBox, infoTree)
    }

    private fun getType(): ConstructionType =
            when (type.selectedToggle) {
                doubleType -> ConstructionType.DOUBLE
                tripleType -> ConstructionType.TRIPLE
                quadroType -> ConstructionType.QUARTET
                solidType -> ConstructionType.SOLID
                else -> ConstructionType.QUARTET
            }

}