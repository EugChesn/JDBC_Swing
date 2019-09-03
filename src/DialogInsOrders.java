import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DialogInsOrders {
    private JFrame parent;
    private JDialog dialog;
    private ArrayList<String> fields;
    private ArrayList<Integer> PizzaInsert;
    private String type;

    DialogInsOrders (JFrame parent,String type)
    {
        fields = new ArrayList<>();
        PizzaInsert = new ArrayList<>();
        this.parent = parent;
        this.type = type;
    }

    private JPanel createEditBox () {
        JPanel Input = new JPanel();
        if (type.equals("insert")) {
            Label NameClient = new Label("Name of Client");
            TextField FieldNameClient = new TextField(18);
            Label DateOrder = new Label("Date of Order");

            JTextField FieldDateOrder = new JTextField(18);
            Label TimeOrder = new Label("Time of order");
            TextField FieldTimeOrder = new TextField(18);

            Label Adress = new Label("Adress");
            TextField FieldAdressOrder = new TextField(18);

            Button Ok = new Button("Ok");
            Button Cancel = new Button("Cancel");

            ArrayList<String> items = Query.getName("pizza", "NamePiz");
            JComboBox<String> Pizza = new JComboBox<String>();
            for (String s : items
                    ) {
                Pizza.addItem(s);
            }
            JTextArea selectPizza = new JTextArea();
            selectPizza.setEditable(false);
            selectPizza.setLineWrap(true);
            selectPizza.setWrapStyleWord(true);

            JScrollPane jsp = new JScrollPane(selectPizza);
            jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

            Input.setLayout(new GridLayout(6, 6));

            Input.add(NameClient);
            Input.add(FieldNameClient);
            Input.add(DateOrder);
            Input.add(FieldDateOrder);
            Input.add(TimeOrder);
            Input.add(FieldTimeOrder);
            Input.add(Adress);
            Input.add(FieldAdressOrder);
            Input.add(Pizza);
            Input.add(selectPizza);
            Input.add(Cancel);
            Input.add(Ok);

            Ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent a) {
                    if (FieldNameClient.getText() == null || FieldNameClient.getText().isEmpty() &&
                            FieldDateOrder.getText() == null || FieldDateOrder.getText().isEmpty() &&
                            FieldTimeOrder.getText() == null || FieldTimeOrder.getText().isEmpty() &&
                            FieldAdressOrder.getText() == null || FieldAdressOrder.getText().isEmpty()) {
                        return;
                    }
                    fields.add(FieldNameClient.getText());
                    fields.add(FieldDateOrder.getText());
                    fields.add(FieldTimeOrder.getText());
                    fields.add(FieldAdressOrder.getText());

                    dialog.dispose();
                }
            });
            Pizza.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String s = (String) Pizza.getSelectedItem();
                    if (!selectPizza.getText().contains(s)) {
                        selectPizza.append(s + "\n");
                        PizzaInsert.add(Query.getId("pizza",
                                "idPizza",
                                "NamePiz",s));
                    }
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
    ArrayList<Integer>getIdIngr(){
        return  PizzaInsert;
    }
}
