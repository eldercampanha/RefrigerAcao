package br.com.refrigeracao.app.presentation.helper;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.widget.EditText;

import com.app.refrigeracao.R;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.regex.Pattern;

import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import br.com.refrigeracao.app.presentation.ui.login.LoginActivity;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by elder-dell on 2017-03-10.
 */

public class EditTextHelper {

    private static Context mContext;

    public EditTextHelper(Context context) {
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

    /**
     * This method does the field verification in text-change-event within using RX.
     *
     * P.S. - empty fields are not considered wrong
     *
     * @param textInputLayout component that contains the EditText that will be validated
     * @param mask #### #### #### ####
     * @param regex ^(4[0-9]{3}) [0-9]{4} [0-9]{4} [0-9]{4}
     */
    public static void addVerificationWithFormat(TextInputLayout textInputLayout, String mask, String regex, String regexMessage){

        // access EditText
        EditText editText = textInputLayout.getEditText();

        // add mask to EditText
        if(mask != null)
            editText.addTextChangedListener(new MaskEditTextChangedListener(mask, editText));

        RxTextView.textChanges(editText).map(new Function<CharSequence, Boolean>() {
            @Override
            public Boolean apply(@NonNull CharSequence charSequence) throws Exception {
                return charSequence.length() == 0 ||
                        editText.getText().toString().trim().matches(regex);
            }
        }).distinctUntilChanged().subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean valid) throws Exception {
                textInputLayout.setError(regexMessage);
                textInputLayout.setErrorEnabled(!valid);
            }
        });

    }
}
