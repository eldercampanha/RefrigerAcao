package br.com.refrigeracao.app.presentation.ui.helper;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.EditText;

import com.app.refrigeracao.R;

import java.util.regex.Pattern;

import br.com.refrigeracao.app.presentation.ui.login.LoginActivity;

/**
 * Created by elder-dell on 2017-03-10.
 */

public class AppHelper {

    private Context mContext;

    public AppHelper(Context context) {
        mContext = context;
    }

    public boolean validateRequiredFields(TextInputLayout... fields){

        boolean isValid = true;

        for (TextInputLayout field  : fields){
            EditText editText = field.getEditText();
            if(editText != null){

                if(TextUtils.isEmpty(editText.getText())){

                    isValid = false;
                    field.setErrorEnabled(true);
                    field.setError(mContext.getString(R.string.txt_required));
                } else {
                    field.setErrorEnabled(false);
                    field.setError(null);
                }

            } else {
                throw new RuntimeException("The TextInputLayout must have an EditText");
            }

        }
        return isValid;
    }

    public boolean validateEmail(TextInputLayout inputEmail) {
        String email = inputEmail.getEditText().getText().toString().trim();
        boolean isValid = true;

        Pattern EMAIL_ADDRESS_PATTERN = Pattern
                .compile("[a-zA-Z0-9+._%-+]{1,256}" + "@"
                        + "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" + "(" + "."
                        + "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" + ")+");
        if(!EMAIL_ADDRESS_PATTERN.matcher(email).matches())
        {
            isValid = false;
            inputEmail.setError(mContext.getString(R.string.valid_email));
            inputEmail.setErrorEnabled(true);
        } else {
            inputEmail.setErrorEnabled(false);
        }

        return isValid;
    }
}
