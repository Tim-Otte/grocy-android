package xyz.zedler.patrick.grocy.model;

import android.app.Activity;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import xyz.zedler.patrick.grocy.R;

public class FormDataShoppingListEdit {
    private final MutableLiveData<String> nameLive;
    private ArrayList<String> shoppingListNames;
    private final ShoppingList startupShoppingList;
    private final MutableLiveData<Integer> nameErrorLive;

    public FormDataShoppingListEdit(ShoppingList shoppingList) {
        nameLive = new MutableLiveData<>();
        nameErrorLive = new MutableLiveData<>();
        startupShoppingList = shoppingList;

        if(startupShoppingList != null) {
            nameLive.setValue(startupShoppingList.getName());
        }
    }

    public MutableLiveData<String> getNameLive() {
        return nameLive;
    }

    public MutableLiveData<Integer> getNameErrorLive() {
        return nameErrorLive;
    }

    public void setShoppingListNames(ArrayList<String> shoppingListNames) {
        this.shoppingListNames = new ArrayList<>(shoppingListNames);
        if(startupShoppingList != null) {
            this.shoppingListNames.remove(startupShoppingList.getName());
        }
    }

    public boolean isActionEdit() {
        return startupShoppingList != null;
    }

    public boolean isNameValid() {
        if(shoppingListNames == null) return false;
        if(nameLive.getValue() == null || nameLive.getValue().isEmpty()) {
            nameErrorLive.setValue(R.string.error_empty);
            return false;
        }
        if(shoppingListNames.contains(nameLive.getValue())) {
            nameErrorLive.setValue(R.string.error_name_exists);
            return false;
        }
        nameErrorLive.setValue(null);
        return true;
    }

    @BindingAdapter({"errorText", "errorActivity"})
    public static void setErrorMessage(
            TextInputLayout view,
            MutableLiveData<Integer> errorMessage,
            Activity activity
    ) {
        if(errorMessage.getValue() != null) {
            view.setError(activity.getString(errorMessage.getValue()));
        } else if(view.isErrorEnabled()) {
            view.setErrorEnabled(false);
        }
    }

    public boolean isFormValid() {
        return isNameValid();
    }
}
