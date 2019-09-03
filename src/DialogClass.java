import javafx.scene.control.ComboBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DialogClass {
    private JFrame parent;
    private JDialog dialog;
    private ArrayList<String> fields;
    private ArrayList<String> listTable;
    private ArrayList<Integer> ingredientsInsert;
    private String type;
    private String nameTable;

    DialogClass (JFrame parent,String type,String nameTable)
    {
        fields = new ArrayList<>();
        ingredientsInsert = new ArrayList<>();
        listTable = new ArrayList<>();
        this.parent = parent;
        this.type = type;
        this.nameTable = nameTable;
    }

    private JPanel createEditBox ()
    {
        JPanel Input = new JPanel();
        if(type.equals("insert")) {
            Label NamePiz = new Label("Name of pizza");
            TextField FieldNamePiz = new TextField(18);
            Label DiamPiz = new Label("Diametr of pizza");
            TextField FieldDiamPiz = new TextField(18);
            Label CostPiz = new Label("Cost of pizza");
            TextField FieldCostPiz = new TextField(18);
            Button Ok = new Button("Ok");
            Button Cancel = new Button("Cancel");

            ArrayList<String>items = Query.getName("ingredients","NameIng");
            JComboBox<String>Ingredients = new JComboBox<String>();
            for (String s:items
                 ) {
                Ingredients.addItem(s);
            }
            JTextArea selectIngredients = new JTextArea();
            selectIngredients.setEditable (false);
            selectIngredients.setLineWrap (true);
            selectIngredients.setWrapStyleWord (true);

            JScrollPane jsp = new JScrollPane (selectIngredients);
            jsp.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            jsp.setHorizontalScrollBarPolicy (ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

            Input.setLayout(new GridLayout(5, 5));

            Input.add(NamePiz);
            Input.add(FieldNamePiz);
            Input.add(DiamPiz);
            Input.add(FieldDiamPiz);
            Input.add(CostPiz);
            Input.add(FieldCostPiz);
            Input.add(Ingredients);
            Input.add(selectIngredients);
            Input.add(Cancel);
            Input.add(Ok);

            Ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent a) {
                    if (FieldNamePiz.getText() == null || FieldNamePiz.getText().isEmpty()&&
                            FieldDiamPiz.getText() == null || FieldDiamPiz.getText().isEmpty()&&
                            FieldCostPiz.getText() == null || FieldCostPiz.getText().isEmpty()){
                        return;
                    }
                    fields.add(FieldNamePiz.getText());
                    fields.add(FieldDiamPiz.getText());
                    fields.add(FieldCostPiz.getText());

                    dialog.dispose();
                }
            });
            Ingredients.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String s = (String)Ingredients.getSelectedItem();
                    if(!selectIngredients.getText().contains(s)){
                        selectIngredients.append(s + "\n");
                        ingredientsInsert.add(Query.getId("ingredients",
                                "idIngredient",
                                "NameIng",s));
                    }
                }
            });
            Cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent a) {
                    dialog.dispose();
                }
            });
        }else if(type.equals("update")){
            ArrayList<String>items = Query.getColumnsName(nameTable);
            JComboBox<String>ColumnsName = new JComboBox<String>();
            for (String s:items
                    ) {
                ColumnsName.addItem(s);
            }
            JComboBox<String>ColumnsNameSearch = new JComboBox<String>();
            for (String s:items
                    ) {
                ColumnsNameSearch.addItem(s);
            }

            JTextArea selectColumnsName = new JTextArea();
            selectColumnsName.setEditable (false);
            selectColumnsName.setLineWrap (true);
            selectColumnsName.setWrapStyleWord (true);
            JScrollPane jsp = new JScrollPane (selectColumnsName);
            jsp.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            jsp.setHorizontalScrollBarPolicy (ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

            JTextArea selectColumnsNameSearch = new JTextArea();
            selectColumnsNameSearch.setEditable (false);
            selectColumnsNameSearch.setLineWrap (true);
            selectColumnsNameSearch.setWrapStyleWord (true);
            JScrollPane jspSearch = new JScrollPane (selectColumnsNameSearch);
            jspSearch.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            jspSearch.setHorizontalScrollBarPolicy (ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

            Label newValField = new Label("New value of field");
            TextField FieldnewValField = new TextField(18);

            Label ValSearch = new Label("Value for search");
            TextField FieldValSearch = new TextField(18);

            Button Ok = new Button("Ok");
            Button Cancel = new Button("Cancel");

            Input.setLayout(new GridLayout(5,5));

            Input.add(ColumnsName);
            Input.add(selectColumnsName);

            Input.add(ColumnsNameSearch);
            Input.add(selectColumnsNameSearch);

            Input.add(newValField);
            Input.add(FieldnewValField);

            Input.add(ValSearch);
            Input.add(FieldValSearch);

            Input.add(Ok);
            Input.add(Cancel);

            ColumnsName.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String s = (String)ColumnsName.getSelectedItem();
                    if(selectColumnsName.getText().isEmpty()){
                        selectColumnsName.append(s + "\n");
                    }
                }
            });
            ColumnsNameSearch.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String s = (String)ColumnsNameSearch.getSelectedItem();
                    if(selectColumnsNameSearch.getText().isEmpty()){
                        selectColumnsNameSearch.append(s + "\n");
                    }
                }
            });
            Ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent a) {
                    if (selectColumnsName.getText() == null || selectColumnsName.getText().isEmpty()&&
                            selectColumnsNameSearch.getText() == null || selectColumnsNameSearch.getText().isEmpty()&&
                            FieldnewValField.getText() == null || FieldnewValField.getText().isEmpty() &&
                            FieldValSearch.getText() == null || FieldValSearch.getText().isEmpty()){
                        return;
                    }
                    fields.add(selectColumnsName.getText());
                    fields.add(FieldnewValField.getText());
                    fields.add(selectColumnsNameSearch.getText());
                    fields.add(FieldValSearch.getText());

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
        else if(type.equals("delete")){
            ArrayList<String>items = Query.getColumnsName(nameTable);
            JComboBox<String>ColumnsName = new JComboBox<String>();
            for (String s:items
                    ) {
                ColumnsName.addItem(s);
            }
            JTextArea selectColumnsName = new JTextArea();
            selectColumnsName.setEditable (false);
            selectColumnsName.setLineWrap (true);
            selectColumnsName.setWrapStyleWord (true);
            JScrollPane jsp = new JScrollPane (selectColumnsName);
            jsp.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            jsp.setHorizontalScrollBarPolicy (ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

            Label LabelValField = new Label("Value for delete");
            TextField ValField  = new TextField(18);
            Button Ok = new Button("Ok");
            Button Cancel = new Button("Cancel");

            Input.setLayout(new GridLayout(3,3));
            Input.add(ColumnsName);
            Input.add(selectColumnsName);

            Input.add(LabelValField);
            Input.add(ValField);

            Input.add(Ok);
            Input.add(Cancel);

            ColumnsName.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String s = (String)ColumnsName.getSelectedItem();
                    if(selectColumnsName.getText().isEmpty()){
                        selectColumnsName.append(s + "\n");
                    }
                }
            });
            Ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent a) {
                    if (selectColumnsName.getText() == null || selectColumnsName.getText().isEmpty()&&
                            ValField.getText() == null || ValField.getText().isEmpty()){
                        return;
                    }
                    fields.add(selectColumnsName.getText());
                    fields.add(ValField.getText());

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
        else if(type.equals("join")){
            ArrayList<String> nameItems = Query.ShowTablesQuery();
            JComboBox<String>nameSourceTable = new JComboBox<>();
            DefaultComboBoxModel<String> ColModel = new DefaultComboBoxModel<>();
            JComboBox<String>nameJoinTable = new JComboBox<>();
            for (String s:nameItems
                    ) {
                nameSourceTable.addItem(s);
                nameJoinTable.addItem(s);
            }

            JComboBox<String>ColumnsName = new JComboBox<>();

            JTextArea selectNameSourceTable = new JTextArea();
            selectNameSourceTable.setEditable (false);
            selectNameSourceTable.setLineWrap (true);
            selectNameSourceTable.setWrapStyleWord (true);
            JScrollPane jsp = new JScrollPane (selectNameSourceTable);
            jsp.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            jsp.setHorizontalScrollBarPolicy (ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

            JTextArea selectNameJoinTable = new JTextArea();
            selectNameJoinTable.setEditable (false);
            selectNameJoinTable.setLineWrap (true);
            selectNameJoinTable.setWrapStyleWord (true);
            JScrollPane jspJoin= new JScrollPane (selectNameJoinTable);
            jspJoin.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            jspJoin.setHorizontalScrollBarPolicy (ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

            JTextArea selectNameColumns = new JTextArea();
            selectNameColumns.setEditable (false);
            selectNameColumns.setLineWrap (true);
            selectNameColumns.setWrapStyleWord (true);
            JScrollPane jspCol = new JScrollPane (selectNameColumns);
            jspCol.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            jspCol.setHorizontalScrollBarPolicy (ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

            Button Apply = new Button("Apply");
            Apply.setActionCommand("Apply");
            Button Clear = new Button("Clear");

            Button Ok = new Button("Ok");
            Button Cancel = new Button("Cancel");

            Input.setLayout(new GridLayout(5,5));

            Input.add(nameSourceTable);
            Input.add(selectNameSourceTable);

            Input.add(nameJoinTable);
            Input.add(selectNameJoinTable);

            Input.add(Apply);
            Input.add(Clear);

            Input.add(ColumnsName);
            Input.add(selectNameColumns);

            Input.add(Ok);
            Input.add(Cancel);

            nameSourceTable.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String s = (String)nameSourceTable.getSelectedItem();
                    if(selectNameSourceTable.getText().isEmpty()){
                        selectNameSourceTable.append(s);
                    }
                }
            });
            nameJoinTable.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String s = (String) nameJoinTable.getSelectedItem();
                    ArrayList<String>linkTable = Query.linkTable(s);
                    if(linkTable != null){
                        for (String str:linkTable
                                ) {
                            if(selectNameSourceTable.getText().equals(str)){
                                selectNameJoinTable.append(s + "\n");
                            }
                        }
                    }
                }
            });
            Apply.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectNameSourceTable.setEditable(false);
                    selectNameJoinTable.setEditable(false);
                    JOptionPane.showMessageDialog(null,"Подтверждение выбранных таблиц","Table",JOptionPane.WARNING_MESSAGE);
                    if(!(selectNameSourceTable.getText() == null) || !selectNameSourceTable.getText().isEmpty() &&
                            !(selectNameJoinTable.getText() == null) || !selectNameJoinTable.getText().isEmpty()) {
                        ArrayList<String> items = Query.getColumnsName(selectNameSourceTable.getText());
                        String[] st = selectNameJoinTable.getText().split("\n");
                        for(int i = 0; i < st.length;i++){
                            for (String s :Query.getColumnsName(st[i]))
                                {
                                items.add(s);
                            }
                        }
                        for (String s : items
                                ) {
                            ColModel.addElement(s);
                        }
                        ColumnsName.setModel(ColModel);
                    }
                }
            });
            Clear.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectNameSourceTable.setText(""); //очищаем текст
                    selectNameJoinTable.setText("");
                    selectNameColumns.setText("");
                    ColumnsName.setModel(new DefaultComboBoxModel<>());
                    ColModel.removeAllElements();
                    JOptionPane.showMessageDialog(null,"Выбор будет очищен","Table",JOptionPane.WARNING_MESSAGE);
                }
            });
            ColumnsName.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String s = (String) ColumnsName.getSelectedItem();
                    System.out.println(s);
                    if (!selectNameColumns.getText().contains(s)) {
                        selectNameColumns.append(s + "\n");
                    }
                    }
            });
            Ok.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(selectNameSourceTable.getText() == null || selectNameSourceTable.getText().isEmpty() &&
                            selectNameJoinTable.getText() == null || selectNameJoinTable.getText().isEmpty() &&
                            selectNameColumns.getText() == null || selectNameColumns.getText().isEmpty()){
                    return;
                    }
                    String[] st = selectNameColumns.getText().split("\n");
                    for(int i = 0;i < st.length;i++){
                        fields.add(st[i]);
                    }
                    listTable.add(selectNameSourceTable.getText());
                    String []nameJoinTable = selectNameJoinTable.getText().split("\n");

                    for(int i = 0;i < nameJoinTable.length;i++){
                        listTable.add(nameJoinTable[i]);
                    }
                    dialog.dispose();
                }
            });
            Cancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dialog.dispose();
                }
            });
        }
        return Input;
    }
    void display ()
    {
        int DWIDTH = 300;
        int DHEIGHT = 400;
        if(type.equals("update")){
            DWIDTH = 220;
            DHEIGHT = 200;
        }else if(type.equals("delete")){
            DWIDTH = 200;
            DHEIGHT = 150;
        }else if(type.equals("join")){
            DWIDTH = 300;
            DHEIGHT = 400;
        }
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
    ArrayList<String>getJoinTable(){
        return listTable;
    }
    ArrayList<Integer>getIdIngr(){
        return  ingredientsInsert;
    }
}
