package co.talagro.app.util;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import co.talagro.app.R;


public class CustomDialog {

    public static void showDialog(Context context, String content) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_layout);

        TextView contentTextView = dialog.findViewById(R.id.contentTextView);
        Button copyButton = dialog.findViewById(R.id.copyButton);

        contentTextView.setText(content);

        copyButton.setOnClickListener(v -> {
            // Copy the content to the clipboard
            copyToClipboard(context, content);
            // Dismiss the dialog
            dialog.dismiss();
        });

        dialog.show();
    }

    private static void copyToClipboard(Context context, String content) {
        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        android.content.ClipData clip = android.content.ClipData.newPlainText("Copied Text", content);
        clipboard.setPrimaryClip(clip);

        Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show();
    }
}
