package com.glasswellapps.iact

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.opengl.Visibility
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.glasswellapps.iact.characters.*
import com.glasswellapps.iact.database.AppDatabase
import com.glasswellapps.iact.database.CharacterData
import com.glasswellapps.iact.effects.Sounds
import kotlinx.android.synthetic.main.activity_character_screen.view.*
import kotlinx.android.synthetic.main.activity_load_screen.*
import kotlinx.android.synthetic.main.dialog_edit_save.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class LoadScreen : AppCompatActivity() {

    var saveDialog: Dialog? = null
    var width = 0f
    var height = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_load_screen)

        Sounds.reset(this)

        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        height = displayMetrics.heightPixels.toFloat()
        width = displayMetrics.widthPixels.toFloat()



        loadData()
        initSaveSlots()
        getCurrentSaveData()

    }

    var selectedFiles = arrayListOf<Int>()
    fun loadData() {
        val database = AppDatabase.getInstance(this)
        MainActivity.data = database!!.getCharacterDAO().getAll()

        showNoSavesFoundToast()
    }

    var currentSaveData = arrayListOf<CharacterData>()
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var adapter:saveSlotAdapter
    fun initSaveSlots() {
        linearLayoutManager = LinearLayoutManager(this)
        saveSlots_recyclerView.layoutManager = linearLayoutManager
        adapter = saveSlotAdapter(currentSaveData, this, this)
        saveSlots_recyclerView.adapter = adapter

    }

    var page = 0
    val slotsPerPage = 5

    fun getCurrentSaveData() {
        currentSaveData.clear()
        getMaxPages()
        pageNumber.text = ""+(page+1) +" / "+maxPages
        for(i in 0..slotsPerPage-1) {
            var dataIndex = page*slotsPerPage+i
            if(dataIndex < MainActivity.data!!.size) {
                currentSaveData.add(MainActivity.data!![dataIndex])
            }
        }
        adapter.notifyDataSetChanged()
    }

    fun onPreviousPage(view: View) {
        Sounds.selectSound()
        if(page > 0){
            page--
        }
        else{
            page = maxPages-1
        }
        getCurrentSaveData()
    }
    fun onNextPage(view: View) {
        Sounds.selectSound()
        if(page<maxPages-1){
            page++
        }
        else{
            page = 0;
        }
        getCurrentSaveData()
    }
    var maxPages = 1
    fun getMaxPages(){
        maxPages = MainActivity.data!!.size/slotsPerPage
        if(MainActivity.data!!.size%slotsPerPage!=0){
            maxPages++
        }
        if(maxPages <1){
            maxPages = 1;
        }
    }


/*
    fun slideAnimation() {

        for (i in 0..saveSlots.size) {
            println("ANIMATED LOAD")
            var saveSlot = saveSlots[i]
            saveSlot.animate().alpha(1f)
            var anim = ObjectAnimator.ofFloat(
                saveSlot,
                "translationX", -(i.toFloat() / 2 + 1) * saveSlot.width.toFloat(),
                0f
            )
            anim.duration = ((i.toFloat() / 2 + 1) * 200).toLong()

            println("" + anim.duration + " " + anim.values[0])

            anim.start()

        }
    }*/
/*
    fun initSaveDialog() {
        saveDialog = Dialog(this)
        saveDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        saveDialog!!.setCancelable(false)
        saveDialog!!.setContentView(R.layout.dialog_edit_save)
        saveDialog!!.setCanceledOnTouchOutside(true)
        saveDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        saveDialog!!.edit_file_name.requestFocus()
        saveDialog!!.apply.setOnClickListener {
            onApply(saveDialog!!.apply)
            saveDialog!!.cancel()
            true
        }
        saveDialog!!.delete.setOnClickListener {
            onDelete(saveDialog!!.delete)


            showNoSavesFoundToast()

            true
        }
    }*/


    fun onSaveSelected(saveDataIndex:Int) {
        Sounds.selectSound()
        for(i in 0..adapter.itemCount) {
            if (i != saveDataIndex) {
                var saveSlot = saveSlots_recyclerView.findViewHolderForAdapterPosition(i)

                if (saveSlot != null) {
                    saveSlot.itemView.visibility = View.INVISIBLE
                }
            }
        }
        val saveData = currentSaveData[saveDataIndex];
        var selectedCharacter = getCharacter(saveData.characterName)

        selectedCharacter.file_name = "" + saveData.fileName
        selectedCharacter.id = saveData.id

        selectedCharacter.damage = saveData.damage
        selectedCharacter.strain = saveData.strain
        selectedCharacter.token = saveData.token
        selectedCharacter.wounded = saveData.wounded

        selectedCharacter.conditionsActive = booleanArrayOf(
            saveData.conditionsActive1,
            saveData.conditionsActive2,
            saveData.conditionsActive3,
            saveData.conditionsActive4,
            saveData.conditionsActive5
        )

        selectedCharacter.totalXP = saveData.totalXP
        selectedCharacter.xpSpent = saveData.xpSpent
        selectedCharacter.xpCardsEquipped = booleanArrayOf(
            saveData.xpCardsEquipped1,
            saveData.xpCardsEquipped2,
            saveData.xpCardsEquipped3,
            saveData.xpCardsEquipped4,
            saveData.xpCardsEquipped5,
            saveData.xpCardsEquipped6,
            saveData.xpCardsEquipped7,
            saveData.xpCardsEquipped8,
            saveData.xpCardsEquipped9
        )

        if (saveData.weapon1 != -1) {
            selectedCharacter.weapons.add(saveData.weapon1)
        }
        if (saveData.weapon2 != -1) {
            selectedCharacter.weapons.add(saveData.weapon2)
        }
        if (saveData.accessory1 != -1) {
            selectedCharacter.accessories.add(saveData.accessory1)
        }
        if (saveData.accessory2 != -1) {
            selectedCharacter.accessories.add(saveData.accessory2)
        }
        if (saveData.accessory3 != -1) {
            selectedCharacter.accessories.add(saveData.accessory3)
        }
        selectedCharacter.helmet = saveData.helmet
        if (saveData.armour != -1) {
            selectedCharacter.armor.add(saveData.armour)
        }


        //TODO rewards and mods
        selectedCharacter.rewards = convertStringToItemID("" + saveData.rewards)
        selectedCharacter.mods = convertStringToItemID("" + saveData.mods)


        selectedCharacter.background = saveData.background.toString()

        selectedCharacter.killCount = arrayOf(
            saveData.killVillian,
            saveData.killLeader,
            saveData.killVehicle,
            saveData.killCreature,
            saveData.killGuard,
            saveData.killDroid,
            saveData.killScum,
            saveData.killTrooper
        )

        selectedCharacter.assistCount = arrayOf(
            saveData.assistVillian,
            saveData.assistLeader,
            saveData.assistVehicle,
            saveData.assistCreature,
            saveData.assistGuard,
            saveData.assistDroid,
            saveData.assistScum,
            saveData.assistTrooper
        )

        selectedCharacter.movesTaken = saveData.moves
        selectedCharacter.attacksMade = saveData.attacks
        selectedCharacter.interactsUsed = saveData.interact
        selectedCharacter.timesWounded = saveData.timesWounded
        selectedCharacter.timesRested = saveData.rested
        selectedCharacter.timesWithdrawn = saveData.timesWithdrawn
        selectedCharacter.activated = saveData.activated
        selectedCharacter.damageTaken = saveData.damageTaken
        selectedCharacter.strainTaken = saveData.strainTaken
        selectedCharacter.specialActions = saveData.special

        selectedCharacter.timesFocused = saveData.timesFocused
        selectedCharacter.timesHidden = saveData.timesHidden
        selectedCharacter.timesStunned = saveData.timesStunned
        selectedCharacter.timesBleeding = saveData.timesBleeding
        selectedCharacter.timesWeakened = saveData.timesWeakened
        selectedCharacter.cratesPickedUp = saveData.cratesPickedUp
        selectedCharacter.rewardObtained = saveData.rewardObtained
        selectedCharacter.withdrawn = saveData.withdrawn

        selectedCharacter.damageAnimSetting = saveData.damageSetting
        selectedCharacter.soundEffectsSetting = saveData.soundEffectsSetting
        selectedCharacter.actionUsageSetting = saveData.actionUsageSetting
        selectedCharacter.imageSetting = saveData.imageSetting
        selectedCharacter.conditionAnimSetting = saveData.conditionAnimSetting

        MainActivity.selectedCharacter = selectedCharacter

        if (MainActivity.selectedCharacter != null) {

            val intent = Intent(this, CharacterScreen::class.java)

            intent.putExtra("CharacterName", selectedCharacter.name_short)
            intent.putExtra("Load", true)
            startActivity(intent)
            finish()

        }
    }

/*
    fun onSaveEdit(saveSlotIndex: Int){

        positionEditing = saveSlotIndex + 5*page
        //saveSlots[saveSlotIndex].animate().alpha(0f)
        saveDialog!!.show()
        saveDialog!!.edit_load_file_name.setText("" + currentSaveData[positionEditing])


    }
*/


    fun convertStringToItemID(itemString: String): ArrayList<Int> {
        var itemIds = arrayListOf<Int>()
        var itemStrings =
            itemString.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()

        for (itemId in itemStrings) {
            if (itemId.isNotEmpty()) {
                itemIds.add(itemId.toInt())
            }
        }
        return itemIds
    }


    fun onFileNameEdited(editedFileName:String, position:Int) {

        Sounds.selectSound()

        MainActivity.data!![position + page*5].fileName = editedFileName
        val database = AppDatabase.getInstance(this)

        database!!.getCharacterDAO().update(MainActivity.data!![position + page*5])
        hideSoftKeyboard()

    }
    fun hideSoftKeyboard(){
        (getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }


    fun onDelete(view: View) {
        Sounds.selectSound()
        selectedFiles


        val deleteWorkRequestBuilder = OneTimeWorkRequest.Builder(deleteWorker::class.java)
        val data = Data.Builder()

        data.putIntArray("selectedFiles", selectedFiles.toIntArray())
        deleteWorkRequestBuilder.setInputData(data.build())
        deleteWorkRequestBuilder.addTag("delete")

        val deleteWorkRequest = deleteWorkRequestBuilder.build()

        WorkManager.getInstance(this).enqueue(deleteWorkRequest)

        WorkManager.getInstance(this)
            .getWorkInfosByTagLiveData("delete")
            .observe(this, Observer<List<WorkInfo>> { workStatusList ->
                val currentWorkStatus = workStatusList?.getOrNull(0)
                if (currentWorkStatus?.state?.isFinished == true) {
                    WorkManager.getInstance(this)
                        .getWorkInfosByTagLiveData("delete").removeObservers(this)
                    println("DELETE FINISHED")
                    startActivity(intent)
                    finish()
                }
            })
    }


    fun getCharacter(characterName: String?): com.glasswellapps.iact.characters.Character {
        var character = Character();
        when (characterName) {
            "biv" -> { character = Character_biv(this) }
            "davith" -> { character = Character_davith(this) }
            "diala" -> { character = Character_diala(this) }
            "drokkatta" -> { character = Character_drokkatta(this) }
            "fenn" -> { character = Character_fenn(this) }
            "gaarkhan" -> { character = Character_gaarkhan(this) }
            "gideon" -> { character = Character_gideon(this) }
            "jarrod" -> { character = Character_jarrod(this) }
            "jyn" -> { character = Character_jyn(this) }
            "loku" -> { character = Character_loku(this) }
            "kotun" -> { character = Character_kotun(this) }
            "mak" -> { character = Character_mak(this) }
            "mhd19" -> { character = Character_mhd19(this) }
            "murne" -> { character = Character_murne(this) }
            "onar" -> { character = Character_onar(this) }
            "saska" -> { character = Character_saska(this) }
            "shyla" -> { character = Character_shyla(this) }
            "verena" -> { character = Character_verena(this) }
            "vinto" -> { character = Character_vinto(this) }
            "ct1701" -> { character = Character_ct1701(this) }
            "tress" -> { character = Character_tress(this) }
            else -> { character = CustomCharacter(this) }
        }
        return character
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    fun showNoSavesFoundToast() {
        if (MainActivity.data!!.isEmpty()) {
            val noSavesFoundToast = Dialog(this)

            noSavesFoundToast.requestWindowFeature(Window.FEATURE_NO_TITLE)
            noSavesFoundToast.setCancelable(false)
            noSavesFoundToast.setContentView(R.layout.dialog_no_savefiles_found)


            noSavesFoundToast.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            noSavesFoundToast!!.show()
            MainScope().launch {
                delay(1000)
                noSavesFoundToast.cancel()

            }
        }
    }

    var isEditing = false
    fun onToggleEdit(view: View) {
        Sounds.selectSound()
        isEditing = !isEditing
        if(isEditing){
            edit_toggle.text = "CANCEL"
        }
        else{
            edit_toggle.text = "EDIT"
        }

        updateToggleAll()
        adapter.notifyDataSetChanged()
    }


    fun onToggleAll(view: View) {
        Sounds.selectSound()
        if(selectedFiles.size < MainActivity.data!!.size){
            selectedFiles.clear()
            for(i in 0..MainActivity.data!!.size-1){
                selectedFiles.add(i)
            }
            all_toggle.text = "NONE"
        }
        else{
            selectedFiles.clear()
            all_toggle.text = "ALL"
        }
        setDeleteButtonVisibility()
        adapter.notifyDataSetChanged()
    }
    fun updateToggleAll(){
        if(isEditing){
            all_toggle.visibility = View.VISIBLE
        }
        else{
            all_toggle.visibility = View.GONE
        }
        if(selectedFiles.size < MainActivity.data!!.size){
            all_toggle.text = "ALL"
        }
        else{
            all_toggle.text = "NONE"
        }
    }



    fun setDeleteButtonVisibility(){
        val deleteButton = findViewById<TextView>(R.id.delete_button)
        if(selectedFiles.size > 0) {
            deleteButton.visibility = View.VISIBLE
            var plural = "S"
            if(selectedFiles.size == 1){plural = ""}
            deleteButton.text = "DELETE "+ selectedFiles.size + " FILE" +plural
        }
        else{
            findViewById<View>(R.id.delete_button).visibility = View.GONE
        }
    }



}

class saveSlotAdapter(private val dataSet: List<CharacterData>, val context:Context, val
loadScreen: LoadScreen) :
    RecyclerView
    .Adapter<saveSlotAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val fileName: TextView
        val characterName: TextView
        val level: TextView
        val portrait: ImageView
        val time: TextView
        val editData: View
        val loadData: View
        val deleteToggle: ImageView
        val editFileName : EditText
        init {
            fileName = view.findViewById(R.id.fileName)
            characterName = view.findViewById(R.id.characterName)
            level = view.findViewById(R.id.level)
            portrait = view.findViewById(R.id.portrait)
            time = view.findViewById(R.id.time)

            editData = view.findViewById(R.id.edit_data)
            loadData = view.findViewById(R.id.load_data)
            deleteToggle = view.findViewById(R.id.delete_toggle)
            editFileName = view.findViewById(R.id.edit_file_name)



        }
    }



    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.save_load_item,
            viewGroup, false)


        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val saveData = dataSet[position]
        viewHolder.fileName.text = saveData.fileName
        viewHolder.editFileName.setText(saveData.fileName)

        viewHolder.characterName.text = getFullName(saveData.characterName)

        var level = 5
        if (saveData.xpSpent <= 1) {
            level = 1
        } else if (saveData.xpSpent <= 4) {
            level = 2
        } else if (saveData.xpSpent <= 7) {
            level = 3
        } else if (saveData.xpSpent <= 10) {
            level = 4
        }
        viewHolder.level.text = "Lv " + level
        var portrait = getCharacterPortrait(saveData.characterName)
        if(portrait!=null) {
            viewHolder.portrait.setImageDrawable(portrait)
        }
        var timeSinceLastSave =  (System.currentTimeMillis()-saveData.date).toFloat()

        var days = (timeSinceLastSave/86400000).toInt()
        var hours = ((timeSinceLastSave - days*86400000)/3600000).toInt()
        var min = ((timeSinceLastSave - days*86400000 - hours*3600000)/60000).toInt()

        var timeAgo = ""
        if(days > 0){
            timeAgo = ""+days+" d ago"
        }
        else if(hours > 0){
            timeAgo = ""+hours+" h " +min +" m ago"
        }
        else {
            timeAgo = ""+min +" m ago"
        }
        viewHolder.time.text = timeAgo

        if(loadScreen.isEditing){
            viewHolder.editData.visibility = View.VISIBLE
            viewHolder.loadData.visibility = View.INVISIBLE
        }
        else{
            viewHolder.editData.visibility = View.INVISIBLE
            viewHolder.loadData.visibility = View.VISIBLE
        }


        setDeleteToggleVisibility(viewHolder.deleteToggle, position)

        viewHolder.deleteToggle.setOnClickListener {
            toggleDelete(it, position)
        }

        viewHolder.loadData.setOnClickListener{
            loadScreen.onSaveSelected(position)
            true
        }

        viewHolder.editFileName.setOnEditorActionListener(object: TextView.OnEditorActionListener{
            override fun onEditorAction(view: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    if (view != null) {
                        loadScreen.onFileNameEdited(""+view.text,position)
                    }
                }
                return false
            }
        })
    }

    fun setDeleteToggleVisibility(view:View, position:Int){
        if(loadScreen.selectedFiles.contains(loadScreen.page*5 + position)){
            view.alpha = 1f
        }
        else{
            view.alpha = 0.3f
        }
    }


    fun toggleDelete(it:View, position:Int){
        Sounds.selectSound()
        val saveDataIndex = loadScreen.page*5 + position

        if(loadScreen.selectedFiles.contains(saveDataIndex)){
            loadScreen.selectedFiles.remove(saveDataIndex)
        }
        else{
            loadScreen.selectedFiles.add(saveDataIndex)
        }

        setDeleteToggleVisibility(it,position)
        loadScreen.setDeleteButtonVisibility()

        loadScreen.updateToggleAll()
        true
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size



    fun getCharacterPortrait(characterName: String?): Drawable? {
        when (characterName) {
            "biv" -> { return context.resources.getDrawable(R.drawable.portrait_biv) }
            "davith" -> { return context.resources.getDrawable(R.drawable.portrait_davith) }
            "diala" -> { return context.resources.getDrawable(R.drawable.portrait_diala) }
            "drokkatta" -> { return context.resources.getDrawable(R.drawable.portrait_drokkatta) }
            "fenn" -> { return context.resources.getDrawable(R.drawable.portrait_fenn) }
            "gaarkhan" -> { return context.resources.getDrawable(R.drawable.portrait_gaarkhan) }
            "gideon" -> { return context.resources.getDrawable(R.drawable.portrait_gideon) }
            "jarrod" -> { return context.resources.getDrawable(R.drawable.portrait_jarrod) }
            "jyn" -> { return context.resources.getDrawable(R.drawable.portrait_jyn) }
            "loku" -> { return context.resources.getDrawable(R.drawable.portrait_loku) }
            "kotun" -> { return context.resources.getDrawable(R.drawable.portrait_kotun) }
            "mak" -> { return context.resources.getDrawable(R.drawable.portrait_mak) }
            "mhd19" -> { return context.resources.getDrawable(R.drawable.portrait_mhd) }
            "murne" -> { return context.resources.getDrawable(R.drawable.portrait_murne) }
            "onar" -> { return context.resources.getDrawable(R.drawable.portrait_onar) }
            "saska" -> { return context.resources.getDrawable(R.drawable.portrait_saska) }
            "shyla" -> { return context.resources.getDrawable(R.drawable.portrait_shyla) }
            "verena" -> { return context.resources.getDrawable(R.drawable.portrait_verena) }
            "vinto" -> { return context.resources.getDrawable(R.drawable.portrait_vinto) }
            "ct1701" -> { return context.resources.getDrawable(R.drawable.portrait_ct) }
            "tress" -> { return context.resources.getDrawable(R.drawable.portrait_tress) }
        }
        return null
    }
    fun getFullName(characterName: String?): String? {
        when (characterName) {
            "biv" -> { return "Biv Bodhrik" }
            "davith" -> { return "Davith Elso" }
            "diala" -> { return "Diala Passil" }
            "drokkatta" -> { return "Drokkatta" }
            "fenn" -> { return "Fenn Signis" }
            "gaarkhan" -> { return "Gaarkhan" }
            "gideon" -> { return "Gideon Argus" }
            "jarrod" -> { return "Jarrod Kelvin" }
            "jyn" -> { return "Jyn Odan" }
            "loku" -> { return "Loku Kanoloa" }
            "kotun" -> { return "Ko-tun Feralo" }
            "mak" -> { return "Mak Eshka'rey" }
            "mhd19" -> { return "MHD-19" }
            "murne" -> { return "Murne Rin" }
            "onar" -> { return "Onar Koma" }
            "saska" -> { return "Saska Teft" }
            "shyla" -> { return "Shyla Varad" }
            "verena" -> { return "Verena Talos" }
            "vinto" -> { return "Vinto Hreeda" }
            "ct1701" -> { return "CT-1701" }
            "tress" -> { return "Tress Hacnua" }
        }
        return characterName
    }
}


class deleteWorker(val context: Context, params: WorkerParameters) : Worker
    (context, params) {
    override fun doWork(): Result {
        var selectedFiles = inputData.getIntArray("selectedFiles")
        if (selectedFiles != null) {
            for(i in 0..selectedFiles.size) {
                val database = AppDatabase.getInstance(context)
                MainActivity.data!![selectedFiles[i]].deleted = true
                //database!!.getCharacterDAO().update(MainActivity.data!![positionEditing])
                database!!.getCharacterDAO().deleteById(MainActivity.data!![selectedFiles[i]].id)
            }
        }
        return Result.success()
    }
}


