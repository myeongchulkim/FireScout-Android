package com.alcherainc.app.firescout.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.alcherainc.app.firescout.Constant;
import com.alcherainc.app.firescout.R;
import com.alcherainc.app.firescout.databinding.FragmentFsMainBinding;

import java.util.Objects;

public class FSMainFragment extends Fragment {

    FragmentFsMainBinding binding;
    OnBackPressedCallback callback;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFsMainBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showUI();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void showUI() {
        WebSettings webSettings = binding.wvMain.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setUseWideViewPort(true);

        binding.wvMain.setWebViewClient(new FSWebViewClient());
        binding.wvMain.addJavascriptInterface(new WebAppInterface(requireContext()), "FS");
        binding.wvMain.loadUrl(Constant.URL);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(binding.wvMain.canGoBack()){
                    binding.wvMain.goBack();
                } else {
                    if (doubleBackToExitPressedOnce) {
                        requireActivity().finish();
                    }

                    doubleBackToExitPressedOnce = true;
                    Toast.makeText(requireContext(), "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2000);
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback.remove();
    }

    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
        }
    }

    private class FSWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            if (request.getUrl().toString().contains("http")) {
                if (request.getUrl().toString().contains("product.firescout.ai")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
                    startActivity(intent);
                } else if (request.getUrl().toString().contains("firescout.ai")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
                    startActivity(intent);
                } else if (request.getUrl().toString().contains("https://docs.google.com/forms")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, request.getUrl());
                    startActivity(intent);
                } else {
                    view.loadUrl(Objects.requireNonNull(request.getUrl().toString()));
                }
                return true;
            }
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            binding.progressMain.layoutProgress.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            binding.progressMain.layoutProgress.setVisibility(View.GONE);
        }
    }
}