package com.glasswellapps.iact
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.room.ColumnInfo
import androidx.work.*
import com.glasswellapps.iact.characters.Character
import com.glasswellapps.iact.characters.CustomCharacter
import com.glasswellapps.iact.database.AppDatabase
import com.glasswellapps.iact.database.CharacterData
import com.glasswellapps.iact.database.CustomData
import kotlinx.android.synthetic.main.activity_create_screen.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Exception

class CreateScreen : AppCompatActivity() {

    var folder:File? = null;
    companion object{
        var customCharacter: CustomCharacter? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_screen)
        customCharacter = CustomCharacter(this)
        val database = AppDatabase.getInstance(this)
        val data = database!!.getCustomDAO().getAll()
        if(data.size>0) {
            customCharacter!!.name = data[0].characterName!!
            customCharacter!!.health_default = data[0].health
            customCharacter!!.endurance_default = data[0].endurance
            customCharacter!!.speed_default = data[0].speed
            customCharacter!!.defence_dice = data[0].defence
        }


        createFolder("/CustomIACharacter")
        createFolder("/CustomIACharacter/xpcards")


        customName.hint = customCharacter!!.name
        customHealth.hint = ""+customCharacter!!.health_default
        customEndurance.hint = ""+customCharacter!!.health_default
        customSpeed.hint = ""+customCharacter!!.health_default
        when(customCharacter!!.defence_dice){
            "black"->{customDefence.setImageDrawable(resources.getDrawable(R.drawable.dice_black))}
            "white"->{customDefence.setImageDrawable(resources.getDrawable(R.drawable.dice))}
        }


        loadImage()
    }

    fun createFolder(folderURL:String){
        folder = File(filesDir,folderURL )
        var success = true
        if(!folder!!.exists()){
            Toast.makeText(getApplication(),"Directory does not exist", Toast.LENGTH_LONG).show();
            success = folder!!.mkdirs()
            if(success){
                Toast.makeText(getApplication(),"Directory created", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplication(),"Failed to create Directory", Toast.LENGTH_LONG).show();
            }
        }
    }
    val PICKFILE_RESULT_CODE = 8778

    fun onPickImage(view: View) {
        var chooseImage = Intent(Intent.ACTION_GET_CONTENT)
        chooseImage.setType("image/*")
        chooseImage = Intent.createChooser(chooseImage,"Choose Image")
        startActivityForResult(chooseImage, PICKFILE_RESULT_CODE)
    }

    var currentImageURL:String = "/CustomIACharacter/tier0image"

    fun loadImage(){
        val file = File(filesDir, currentImageURL)
        if(file.exists()) {
            var inputStream:FileInputStream? = null
            try{
                inputStream = FileInputStream(file)
            }
            catch (e:Exception){
                e.printStackTrace()
            }
            var bitmap = android.graphics.BitmapFactory.decodeStream(inputStream)
            imagePicked.setImageBitmap(bitmap)
        }
        else{
            Toast.makeText(getApplication(),currentImageURL + " not found", Toast.LENGTH_LONG).show();
            imagePicked.setImageDrawable(resources.getDrawable(R.drawable.empty_item_slot))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode) {
            PICKFILE_RESULT_CODE -> {
                if(resultCode == -1){
                    var imageUri = data!!.data!!;
                    var image = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                    println("image "+imageUri+""+image)

                    var file = File(filesDir, currentImageURL)
                    if(file.exists()){
                        file.delete()
                    }
                    try{
                        val out = FileOutputStream(file)
                        image.compress(Bitmap.CompressFormat.PNG,100,out)
                        out.flush()
                        out.close()
                    }
                    catch (e:Exception){
                        e.printStackTrace()
                    }

                    loadImage()
                }
            }
        }
    }

    override fun onBackPressed(){
        customCharacter!!.name = ""+customName.text.toString()
        if(customHealth.text.toString().isNotEmpty()) {
            customCharacter!!.health_default = Integer.parseInt(customHealth.text.toString())
        }
        if(customEndurance.text.toString().isNotEmpty()) {
            customCharacter!!.endurance_default = Integer.parseInt(customEndurance.text.toString())
        }
        if(customSpeed.text.toString().isNotEmpty()) {
            customCharacter!!.speed_default = Integer.parseInt(customSpeed.text.toString())
        }

        save()
        super.onBackPressed()
    }

    fun save() {
        val saveWorkRequestBuilder = OneTimeWorkRequest.Builder(saveCustomWorker::class.java)
        val data = Data.Builder()

        saveWorkRequestBuilder.setInputData(data.build())
        saveWorkRequestBuilder.addTag("save")

        val saveWorkRequest =saveWorkRequestBuilder.build()

        WorkManager.getInstance(this).enqueue(saveWorkRequest)
        WorkManager.getInstance(this)
            .getWorkInfosByTagLiveData("save")
            .observe(this, Observer<List<WorkInfo>> {
                    workStatusList ->
                val currentWorkStatus = workStatusList ?.getOrNull(0)
                if (currentWorkStatus ?.state ?.isFinished == true)
                {
                    WorkManager.getInstance(this)
                        .getWorkInfosByTagLiveData("save").removeObservers(this)
                    println("Save Finished")


                }
            })
    }

    fun deselectImageButtons(){
        imageDefault.alpha=0.5f;
        imageTier1.alpha=0.5f;
        imageTier2.alpha=0.5f;
        imageTier3.alpha=0.5f;

        imagePower.alpha=0.5f;
        imageRanged.alpha=0.5f;
        imageMelee.alpha=0.5f;
        imagePortrait.alpha=0.5f;
        imageHelmet.alpha=0.5f;
    }
    fun onDefaultImage(view: View) {
        currentImageURL = "/CustomIACharacter/tier0image"
        deselectImageButtons()
        imageDefault.alpha = 1f
        loadImage()
    }
    fun onTier1Image(view: View) {
        currentImageURL = "/CustomIACharacter/tier1image"
        deselectImageButtons()
        imageTier1.alpha = 1f
        loadImage()
    }
    fun onTier2Image(view: View) {
        currentImageURL = "/CustomIACharacter/tier2image"
        deselectImageButtons()
        imageTier2.alpha = 1f
        loadImage()
    }
    fun onTier3Image(view: View) {
        currentImageURL = "/CustomIACharacter/tier3image"
        deselectImageButtons()
        imageTier3.alpha = 1f
        loadImage()
    }
    fun onMeleeImage(view: View) {
        currentImageURL = "/CustomIACharacter/xpcards/startingWeaponMelee"
        deselectImageButtons()
        imageMelee.alpha = 1f
        loadImage()
    }
    fun onRangedImage(view: View) {
        currentImageURL = "/CustomIACharacter/xpcards/startingWeaponRanged"
        deselectImageButtons()
        imageRanged.alpha = 1f
        loadImage()
    }
    fun onPortraitImage(view: View) {
        currentImageURL = "/CustomIACharacter/portraitImage"
        deselectImageButtons()
        imagePortrait.alpha = 1f
        loadImage()
    }
    fun onHelmetImage(view: View) {
        currentImageURL = "/CustomIACharacter/helmet"
        deselectImageButtons()
        imageHelmet.alpha = 1f
        loadImage()
    }
    fun onPowerImage(view: View) {
        currentImageURL = "/CustomIACharacter/power"
        deselectImageButtons()
        imagePower.alpha = 1f
        loadImage()
    }



}
class saveCustomWorker(val context: Context, params: WorkerParameters): Worker
    (context,
    params) {


    override fun doWork(): Result {
        val database = AppDatabase.getInstance(context)
        var saveFile = getCharacterData(CreateScreen.customCharacter!!.file_name)
        if (CreateScreen.customCharacter!!.id != -1) {
            saveFile.id = CreateScreen.customCharacter!!.id
            database!!.getCustomDAO().update(saveFile)
            println("update save")
        } else {

            CreateScreen.customCharacter!!.id = database!!.getCustomDAO().getPrimaryKey(database!!.getCustomDAO().insert(saveFile))
            println("insert save")

        }

        return Result.success()
    }

    fun getCharacterData(fileName: String): CustomData {
        val character = CreateScreen.customCharacter!!;
        var data = CustomData(
            fileName,
            character.name,
            character.health_default,
            character.endurance_default,
            character.speed_default,
            character.defence_dice,
            character.strength,
            character.insight,
            character.tech,
            character.strengthWounded,
            character.insightWounded,
            character.techWounded,
            character.xpEndurances[0],
            character.xpEndurances[1],
            character.xpEndurances[2],
            character.xpEndurances[3],
            character.xpEndurances[4],
            character.xpEndurances[5],
            character.xpEndurances[6],
            character.xpEndurances[7],
            character.xpHealths[0],
            character.xpHealths[1],
            character.xpHealths[2],
            character.xpHealths[3],
            character.xpHealths[4],
            character.xpHealths[5],
            character.xpHealths[6],
            character.xpHealths[7],
            character.xpSpeeds[0],
            character.xpSpeeds[1],
            character.xpSpeeds[2],
            character.xpSpeeds[3],
            character.xpSpeeds[4],
            character.xpSpeeds[5],
            character.xpSpeeds[6],
            character.xpSpeeds[7],
            character.bio_title,
            character.bio_quote,
            character.bio_text
        )


        return data
    }
}
