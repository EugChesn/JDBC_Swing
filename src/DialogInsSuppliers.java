import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DialogInsSuppliers {
    private JFrame parent;
    private JDialog dialog;
    private ArrayList<String> fields;
    private ArrayList<String> fieldsIng;
    private String type;

    DialogInsSuppliers (JFrame parent,String type)
    {
        fields = new ArrayList<>();
        fieldsIng = new ArrayList<>();
        this.parent = parent;
        this.type = type;
    }

    private JPanel createEditBox () {
        JPanel Input = new JPanel();
        if (type.equals("insert")) {
            Label NameSupp = new Label("Name of Supplier");
            TextField FieldNameSupp = new TextField(18);
            Label ContactName = new Label("Contact name");
            TextField FieldContactName = new TextField(18);
            Label ContactPhone = new Label("Contact Phone");
            TextField FieldContactPhone = new TextField(18);
            Label NameIng = new Label("Name of ingredients");
            TextField FieldNameIng = new TextField(18);
            Label UnitPrice = new Label("Unit price");
            TextField FieldUnitPrice = new TextField(18);

            Button Ok = new Button("Ok");
            Button Cancel = new Button("Cancel");

            Input.setLayout(new GridLayout(6, 6));

            Input.add(NameSupp);
            Input.add(FieldNameSupp);
            Input.add(ContactName);
            Input.add(FieldContactName);
            Input.add(ContactPhone);
            Input.add(FieldContactPhone);
            Input.add(NameIng);
            Input.add(FieldNameIng);
            Input.add(UnitPrice);
            Input.add(FieldUnitPrice);
            Input.add(Cancel);
            Input.add(Ok);

            Ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent a) {
                    if (FieldNameSupp.getText() == null || FieldNameSupp.getText().isEmpty() &&
                            FieldContactName.getText() == null || FieldContactName.getText().isEmpty() &&
                            FieldContactPhone.getText() == null || FieldContactPhone.getText().isEmpty() &&
                            FieldNameIng.getText() == null || FieldNameIng.getText().isEmpty() &&
                            FieldUnitPrice.getText() == null || FieldUnitPrice.getText().isEmpty()) {
                        return;
                    }
                    fields.add(FieldNameSupp.getText());
                    fields.add(FieldContactName.getText());
                    fields.add(FieldContactPhone.getText());

                    fieldsIng.add(FieldNameIng.getText());
                    fieldsIng.add(FieldUnitPrice.getText());

                    dialog.dispose();
                }
            });
            Cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent a) {
                    dialog.dispose();
                }
            });
        }
        return Input;
    }
    void display ()
    {
        final int DWIDTH = 300;
        final int DHEIGHT = 400;

        dialog = new JDialog (parent, "Input information", true);
        dialog.setSize (DWIDTH, DHEIGHT);
        dialog.setResizable (false);
        dialog.setDefaultCloseOperation (JDialog.DISPOSE_ON_CLOSE);

        dialog.setContentPane (createEditBox ());
        dialog.setLocationRelativeTo (parent);
        dialog.setVisible (true);
    }

    ArrayList<String> getFields () {
        return fields;
    }
    ArrayList<String>getFieldsIngr(){
        return  fieldsIng;
    }
}
