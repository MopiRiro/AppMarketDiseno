package com.example.appmarketdiseno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class OnboardingScreenActivity extends AppCompatActivity {

    private OnboardingAdapter onboardingAdapter;
    private LinearLayout layoutOnboardingIndicators;
    private MaterialButton buttonOnboardingAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding_screen);

        layoutOnboardingIndicators=findViewById(R.id.layoutOnboardingIndicators);
        buttonOnboardingAction = findViewById(R.id.buttonOnboardingAction);

        setupOnboardingItems();

        ViewPager2 onboardingViewPager= findViewById(R.id.onboardingViewPager);
        onboardingViewPager.setAdapter(onboardingAdapter);

        setupOnboardingIndicators();
        setCurrentOnboardingIndicator(0);

        onboardingViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentOnboardingIndicator(position);
            }
        });

        buttonOnboardingAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onboardingViewPager.getCurrentItem()+1<onboardingAdapter.getItemCount()){
                    onboardingViewPager.setCurrentItem(onboardingViewPager.getCurrentItem()+1);
                }else {
                    startActivity(new Intent(getApplicationContext(),DashboardScreenActivity.class));
                    finish();
                }
            }
        });

    }

    private void setupOnboardingItems(){
        List<OnboardingItem> onboardingItems = new ArrayList<>();

        OnboardingItem itemPagos= new OnboardingItem();
        itemPagos.setTittle("Paga tu compra online");
        itemPagos.setDescripcion("Puede relizar sus pagos online, mediante su celular");
        itemPagos.setImage(R.drawable.view1);

        OnboardingItem itemProductos= new OnboardingItem();
        itemProductos.setTittle("Productos de calidad");
        itemProductos.setDescripcion("Productos variados y de calidac");
        itemProductos.setImage(R.drawable.view2);

        OnboardingItem itemBioseguridad= new OnboardingItem();
        itemBioseguridad.setTittle("Protocolos bioseguridad");
        itemBioseguridad.setDescripcion("Cumplimos con todos los protoolos de bioseguridad");
        itemBioseguridad.setImage(R.drawable.view3);

        onboardingItems.add(itemPagos);
        onboardingItems.add(itemProductos);
        onboardingItems.add(itemBioseguridad);

        onboardingAdapter = new OnboardingAdapter(onboardingItems);
    }

    private void setupOnboardingIndicators(){
        ImageView[] indicators = new ImageView[onboardingAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8,0,8,0);

        for(int i = 0; i <indicators.length; i ++)
        {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_inactive));

            indicators[i].setLayoutParams(layoutParams);
            layoutOnboardingIndicators.addView(indicators[i]);

        }
    }

    private void setCurrentOnboardingIndicator(int index){
        int childCount = layoutOnboardingIndicators.getChildCount();
        for (int i = 0; i < childCount; i ++){
            ImageView imageView = (ImageView) layoutOnboardingIndicators.getChildAt(i);
            if (i==index){
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_active));
            }else{
                imageView.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.onboarding_indicator_inactive));
            }
        }

        if(index == onboardingAdapter.getItemCount()-1){
            buttonOnboardingAction.setText("Iniciar");
        }else{
            buttonOnboardingAction.setText("Siguiente");
        }
    }
}