package sealwing.elephant.desktop.core

data class Profile(val type: ProfileType, val length: Float, val amount: Int)

class Deal(firstConstruction: Construction, var id: String = "N/A", var address: String = "N/A") {

    val constructionList = ArrayList<Construction>()
    val supplementList = HashMap<SupplementType, Int>()

    init {
        addConstruction(firstConstruction)
    }

    fun addConstruction(stack: Construction) {
        constructionList.add(stack)
    }

    fun removeConstruction(id: Int) {
        constructionList.remove(constructionList[id])
    }

    // SUPPLEMENTS

    fun addSupplement(type: SupplementType, amount: Int = 1) {
        val finAmount = if (supplementList.containsKey(type)) supplementList.getValue(type) + amount else amount
        supplementList.put(type, finAmount)
    }

    fun removeSupplement(type: SupplementType) {
        if (supplementList.containsKey(type)) {
            val amount = supplementList.getValue(type)
            if (amount > 0) {
                supplementList.put(type, amount - 1)
            }
        }
    }

    fun deleteLast(): Boolean =
            if (constructionList.lastIndex >= 0) {
                removeConstruction(constructionList.size - 1)
                true
            } else {
                false
            }

    fun deleteAll() {
        constructionList.clear()
        supplementList.clear()
    }
}

class Construction(val type: ConstructionType, var width: Int, var height: Int, var amount: Int = 1) {

    val profilesList = ArrayList<Profile>()
    var fitWidth = 0f
    var fitHeight = 0f

    init {
        when (type) {
            ConstructionType.DOUBLE -> initDouble()
            ConstructionType.QUARTET -> initQuartet()
            ConstructionType.TRIPLE -> initTriple()
            ConstructionType.SOLID -> initSolid()
        }
    }

    // INIT CONSTRUCTIONS

    private fun initDouble() {
        val horLen = (width - 33).toFloat() / 2
        val verLen = (height - 53).toFloat()
        profilesList.add(Profile(ProfileType.HORIZONTAL, horLen, 4))
        profilesList.add(Profile(ProfileType.INNER, verLen, 2))
        profilesList.add(Profile(ProfileType.OUTER, verLen, 2))
        profilesList.add(Profile(ProfileType.HORIZONTAL_FRAME, (width - 40).toFloat(), 2))
        profilesList.add(Profile(ProfileType.VERTICAL_FRAME, height.toFloat(), 2))
        fitWidth = horLen - 67
        fitHeight = verLen - 85
    }

    private fun initQuartet() {
        val horLen = (width - 24).toFloat() / 4
        val verLen = (height - 53).toFloat()
        profilesList.add(Profile(ProfileType.HORIZONTAL, horLen, 8))
        profilesList.add(Profile(ProfileType.INNER, verLen, 4))
        profilesList.add(Profile(ProfileType.OUTER, verLen, 4))
        profilesList.add(Profile(ProfileType.HORIZONTAL_FRAME, (width - 40).toFloat(), 2))
        profilesList.add(Profile(ProfileType.VERTICAL_FRAME, height.toFloat(), 2))
        profilesList.add(Profile(ProfileType.STULP, verLen, 1))
        fitWidth = horLen - 67
        fitHeight = verLen - 85
    }

    private fun initTriple() {
        val horLen = ((width - 62).toFloat() / 3)
        val verLen = (height - 53).toFloat()
        profilesList.add(Profile(ProfileType.HORIZONTAL, horLen, 6))
        profilesList.add(Profile(ProfileType.INNER, verLen, 2))
        profilesList.add(Profile(ProfileType.OUTER, verLen, 4))
        profilesList.add(Profile(ProfileType.HORIZONTAL_FRAME, (width - 40).toFloat(), 2))
        profilesList.add(Profile(ProfileType.VERTICAL_FRAME, height.toFloat(), 2))
        profilesList.add(Profile(ProfileType.STULP, verLen, 1))
        fitWidth = horLen - 67
        fitHeight = verLen - 85
    }


    private fun initSolid() {
        profilesList.add(Profile(ProfileType.SOLID_FIT, (width - 46).toFloat(), 2))
        profilesList.add(Profile(ProfileType.SOLID_FIT, (height - 76).toFloat(), 2))
        profilesList.add(Profile(ProfileType.SOLID_FRAME, (width).toFloat(), 2))
        profilesList.add(Profile(ProfileType.SOLID_FRAME, (height).toFloat(), 2))
        fitWidth = (width - 54).toFloat()
        fitHeight = (height - 54).toFloat()
    }

}