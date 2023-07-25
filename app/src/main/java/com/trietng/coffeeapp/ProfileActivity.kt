package com.trietng.coffeeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.trietng.coffeeapp.database.viewmodel.UserViewModel
import com.trietng.coffeeapp.database.viewmodel.UserViewModelFactory
import org.w3c.dom.Text

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        // New array of 4 EditText views
        val editText = arrayListOf<EditText>()

        // Get EditText views
        editText.add(findViewById(R.id.edit_text_fullname))
        editText.add(findViewById(R.id.edit_text_email))
        editText.add(findViewById(R.id.edit_text_phone))
        editText.add(findViewById(R.id.edit_text_address))

        // Set text
        userViewModel.getUser.observe(this) {
            it.let {
                editText[0].setText(it.fullname)
                editText[1].setText(it.email)
                editText[2].setText(it.phone)
                editText[3].setText(it.address)
            }
        }

        // EditText are not editable by default
        for (element in editText) {
            element.isEnabled = false
        }

        // New array of 4 ImageView views
        val imageView = arrayListOf<ImageView>()

        // Get view for edit ImageView
        imageView.add(findViewById(R.id.image_view_edit_fullname))
        imageView.add(findViewById(R.id.image_view_edit_email))
        imageView.add(findViewById(R.id.image_view_edit_phone))
        imageView.add(findViewById(R.id.image_view_edit_address))

        // Set onClickListener for edit ImageView
        for (i in 0..3) {
            editText[i].setOnFocusChangeListener {
                view, hasFocus ->
                if (!hasFocus) {
                    if ((view as TextView).text.isNotBlank()) {
                        when (i) {
                            0 -> userViewModel.updateFullname(view.text.toString())
                            1 -> userViewModel.updateEmail(view.text.toString())
                            2 -> userViewModel.updatePhone(view.text.toString())
                            3 -> userViewModel.updateAddress(view.text.toString())
                        }
                    }
                }
            }

            imageView[i].setOnClickListener {
                if (editText[i].isEnabled) {
                    editText[i].isEnabled = false
                    imageView[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_edit))
                    for (j in 0..3) {
                        imageView[j].isEnabled = true
                    }
                }
                else {
                    editText[i].isEnabled = true
                    editText[i].requestFocus()
                    editText[i].setSelection(editText[i].text.length)
                    imageView[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_check))
                    // disable other image views
                    // Inefficient but the array is short so it is fine
                    for (j in 0..3) {
                        imageView[j].isEnabled = false
                    }
                    imageView[i].isEnabled = true
                }
            }
        }
    }

    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as CoffeeApplication).repository)
    }
}