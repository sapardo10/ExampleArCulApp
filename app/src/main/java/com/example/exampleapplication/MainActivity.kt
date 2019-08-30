package com.example.exampleapplication


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RawRes
import androidx.appcompat.app.AppCompatActivity
import com.cul.arcul.ARModelConfigurator
import com.cul.arcul.VisualActivity

class MainActivity : AppCompatActivity() {

    companion object {
        private val TUCURINCA = "Tucurinca"
        private val SOFA = "Sofa"
        private val PAINTING = "Painting"
        private val URL = "url"
        private val ENABLED = "Enabled"
        private val DISABLED = "Disabled"
    }

    private val button: Button by lazy {
        findViewById<Button>(R.id.open_ar)
    }
    private val buttonSofa: Button by lazy {
        findViewById<Button>(R.id.button_sofa)
    }
    private val buttonTucurinca: Button by lazy {
        findViewById<Button>(R.id.button_tucurinca)
    }
    private val buttonPainting: Button by lazy {
        findViewById<Button>(R.id.button_painting)
    }
    private val buttonUrl: Button by lazy {
        findViewById<Button>(R.id.button_url)
    }
    private val modelSelected: TextView by lazy {
        findViewById<TextView>(R.id.model_selected)
    }
    private val buttonRotation: Button by lazy {
        findViewById<Button>(R.id.button_rotation)
    }
    private val buttonPlane: Button by lazy {
        findViewById<Button>(R.id.button_plane)
    }
    private val urlLabel: TextView by lazy {
        findViewById<TextView>(R.id.url_label)
    }
    private val urlText: EditText by lazy {
        findViewById<EditText>(R.id.et_url)
    }
    @RawRes
    var resourceId: Int = R.raw.sofa
    var modelSelectedString: String = SOFA
    var rotationEnabled = true
    var planeSelected = VisualActivity.HORIZONTAL_PLANE
    var name = "Sofa"
    var height = 100.0
    var width = 100.0
    var depth = 100.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ARModelConfigurator.instance.configureAR(
            ARModelConfigurator.HORIZONTAL_PLANE,
            rotationEnabled = true,
            videoEnabled = true,
            moveEnabled = true
        )
        ARModelConfigurator.instance.injectIdModel(resourceId)
        ARModelConfigurator.instance.injectModelInfo(height, width, depth, name)
        setOnClickListeners()
        changeTextModelSelected()
        changeTextRotationButton()
        changeTextPlaneButton()
        ARModelConfigurator.instance.configureAR()
    }

    private fun changeTextPlaneButton() {
        buttonPlane.text = planeSelected
    }

    private fun setOnClickListeners() {
        openARButtonClickListener()
        sofaButtonClickListener()
        tucurincaButtonClickListener()
        paintingButtonClickListener()
        urlButtonClickListener()
        rotationButtonClickListener()
        planeButtonClickListener()
    }

    private fun planeButtonClickListener() {
        buttonPlane.setOnClickListener {
            planeSelected = if (planeSelected.equals(VisualActivity.HORIZONTAL_PLANE)) {
                VisualActivity.VERTICAL_PLANE
            } else {
                VisualActivity.HORIZONTAL_PLANE
            }
            changeTextPlaneButton()
        }
    }

    private fun openARButtonClickListener() {
        button.setOnClickListener {
            if (modelSelectedString == URL && urlText.text.toString().trim().isEmpty()) {
                Toast.makeText(this, "You should include some url on the field", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (modelSelectedString == URL) {
                ARModelConfigurator.instance.injectURI(urlText.text.toString())
            }
            val intent = Intent(this, VisualActivity::class.java)
            intent.putExtra(
                VisualActivity.MODEL_URI,
                "https://poly.googleusercontent.com/downloads/c/fp/1559555083762887/6acMdZwnCkQ/4PnOFxypYbU/Modern_Rug.gltf"
            )
            startActivity(intent)


        }
    }

    private fun changeVisibilityUrlFields(visibility: Int) {
        urlLabel.visibility = visibility
        urlText.visibility = visibility
    }

    private fun sofaButtonClickListener() {
        buttonSofa.setOnClickListener {
            changeVisibilityUrlFields(View.INVISIBLE)
            modelSelectedString = SOFA
            changeTextModelSelected()

            ARModelConfigurator.instance.configureAR(
                ARModelConfigurator.HORIZONTAL_PLANE,
                rotationEnabled = true,
                videoEnabled = true,
                moveEnabled = true
            )

            resourceId = R.raw.sofa
            name = "Sofa"
            height = 150.0
            width = 300.0
            depth = 98.0
            ARModelConfigurator.instance.injectIdModel(resourceId)
            ARModelConfigurator.instance.injectModelInfo(this.height, width, depth, name)
        }
    }

    private fun tucurincaButtonClickListener() {
        buttonTucurinca.setOnClickListener {
            changeVisibilityUrlFields(View.INVISIBLE)
            modelSelectedString = TUCURINCA
            changeTextModelSelected()
            resourceId = R.raw.tucurinca
            name = "Tucurinca con nombre muuuuy largo para probar watermark"
            height = 120.0
            width = 102.0
            depth = 100.0
            ARModelConfigurator.instance.configureAR(
                ARModelConfigurator.HORIZONTAL_PLANE,
                rotationEnabled = true,
                videoEnabled = true,
                moveEnabled = true
            )
            ARModelConfigurator.instance.injectIdModel(resourceId)
            ARModelConfigurator.instance.injectModelInfo(this.height, width, depth, name)
        }
    }

    private fun paintingButtonClickListener() {
        buttonPainting.setOnClickListener {
            changeVisibilityUrlFields(View.INVISIBLE)
            modelSelectedString = PAINTING
            changeTextModelSelected()
            resourceId = R.raw.painting
            name = "Painting"
            height = 60.0
            width = 30.0
            depth = 0.0

            ARModelConfigurator.instance.configureAR(
                ARModelConfigurator.HORIZONTAL_PLANE,
                rotationEnabled = true,
                videoEnabled = true,
                moveEnabled = true
            )
            ARModelConfigurator.instance.injectIdModel(resourceId)
            ARModelConfigurator.instance.injectModelInfo(this.height, width, depth, name)
        }
    }

    private fun urlButtonClickListener() {
        buttonUrl.setOnClickListener {
            changeVisibilityUrlFields(View.VISIBLE)
            modelSelectedString = URL
            changeTextModelSelected()
            name = "Injected by Url"
            height = 60.0
            width = 30.0
            depth = 1.0

            ARModelConfigurator.instance.configureAR(
                ARModelConfigurator.HORIZONTAL_PLANE,
                rotationEnabled = true,
                videoEnabled = true,
                moveEnabled = true
            )
            ARModelConfigurator.instance.injectURI(urlText.text.toString())
            ARModelConfigurator.instance.injectModelInfo(this.height, width, depth, name)
        }
    }

    private fun rotationButtonClickListener() {
        buttonRotation.setOnClickListener {
            rotationEnabled = !rotationEnabled
            changeTextRotationButton()
        }
    }

    private fun changeTextModelSelected() {
        modelSelected.text = modelSelectedString

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (modelSelectedString == TUCURINCA) {
                buttonTucurinca.setBackgroundColor(resources.getColor(R.color.colorPrimary, theme))

                buttonSofa.setBackgroundColor(resources.getColor(R.color.white, theme))
                buttonPainting.setBackgroundColor(resources.getColor(R.color.white, theme))
                buttonUrl.setBackgroundColor(resources.getColor(R.color.white, theme))
            } else if (modelSelectedString == SOFA) {
                buttonTucurinca.setBackgroundColor(resources.getColor(R.color.white, theme))
                buttonSofa.setBackgroundColor(resources.getColor(R.color.colorPrimary, theme))
                buttonPainting.setBackgroundColor(resources.getColor(R.color.white, theme))
                buttonUrl.setBackgroundColor(resources.getColor(R.color.white, theme))
            } else if (modelSelectedString == PAINTING) {
                buttonTucurinca.setBackgroundColor(resources.getColor(R.color.white, theme))
                buttonSofa.setBackgroundColor(resources.getColor(R.color.white, theme))
                buttonPainting.setBackgroundColor(resources.getColor(R.color.colorPrimary, theme))
                buttonUrl.setBackgroundColor(resources.getColor(R.color.white, theme))
            } else {
                buttonTucurinca.setBackgroundColor(resources.getColor(R.color.white, theme))
                buttonSofa.setBackgroundColor(resources.getColor(R.color.white, theme))
                buttonPainting.setBackgroundColor(resources.getColor(R.color.white, theme))
                buttonUrl.setBackgroundColor(resources.getColor(R.color.colorPrimary, theme))
            }
        }
    }

    private fun changeTextRotationButton() {
        buttonRotation.text = when (rotationEnabled) {
            true -> ENABLED
            false -> DISABLED
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var color = resources.getColor(R.color.colorPrimary, theme)
            if (!rotationEnabled) {
                color = resources.getColor(R.color.white, theme)
            }
            buttonRotation.setBackgroundColor(color)
        }
    }
}
