package com.tas.speech.form.elements.lov;

import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.tas.speech.R;
import com.tas.speech.form.FormElementModel;
import com.tas.speech.form.adapters.LovAdapter;
import com.tas.speech.form.elements.FormElement;

public class LovFormElement extends FormElement {

    private TextView tvSelectedValue;

    public LovFormElement(Context ctx, FormElementModel elementModel) {
        super(ctx, elementModel);

        if(elementModel.getDefaultValueIndex() != -1 && elementModel.getDefaultValueIndex() >= 0
                && elementModel.getDefaultValueIndex() < elementModel.getValues().length) {
            value = elementModel.getValues()[elementModel.getDefaultValueIndex()];
        }
        else {
            value = "";
        }
    }

    public void prepareElement(ViewGroup formContainer) {

        LayoutInflater layoutInflater = (LayoutInflater)
                mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        elementView = layoutInflater.inflate(R.layout.layout_form_lov,
                formContainer, false);

        tvLabel = (TextView)elementView.findViewById(R.id.tv_form_lov_label);
        tvLabel.setText(Html.fromHtml(mCtx.getString(mElementModel.getLabelRes())));

        tvSelectedValue = (TextView)elementView.findViewById(R.id.et_form_lov_value);
        if(value == null || value.equals("")) {
            tvSelectedValue.setText(mCtx.getString(mElementModel.getHintRes()));
        }
        else if(!value.equals("")) {
            tvSelectedValue.setTextColor(mCtx.getResources().getColor(R.color.black));
            tvSelectedValue.setText(value);
        }


        elementView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(mCtx, R.style.OptionsDialog);
                dialog.setContentView(R.layout.layout_dialog_options);

                TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_select_title);
                tvTitle.setText(mElementModel.getLovTitle());

                ListView lvOptions = (ListView) dialog.findViewById(R.id.lv_options);
                LovAdapter arrayAdapter = new LovAdapter(mCtx, R.layout.layout_dialog_options_row,
                        mElementModel.getValues(), value);
                lvOptions.setAdapter(arrayAdapter);

                lvOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        value = ((LovAdapter) parent.getAdapter()).getObjects()[position];

                        tvSelectedValue.setTextColor(mCtx.getResources().getColor(R.color.sign_up_text));
                        tvSelectedValue.setText(value);

                        dialog.dismiss();
                    }
                });

                lvOptions.setSelection(mElementModel.getValues().length / 2);

                dialog.show();
            }
        });
    }

    public void displayElement(ViewGroup formContainer) {
        formContainer.addView(elementView, 0);
    }

    public boolean validate() {
        boolean elementValid = super.validate();

        if(!elementValid) {
            tvSelectedValue.setBackgroundDrawable(mCtx.getResources()
                    .getDrawable(R.drawable.spinner_layer_invalid));
            tvLabel.setTextColor(mCtx.getResources().getColor(R.color.red));
        }
        else {
            tvSelectedValue.setBackgroundDrawable(mCtx.getResources()
                    .getDrawable(R.drawable.spinner_layer_valid));
            tvLabel.setTextColor(mCtx.getResources().getColor(R.color.green_dark));
        }

        return elementValid;
    }
}
