import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;


public class MainForm extends JFrame {
    final private static String pizza = "pizza";
    final private static String orders = "orders";
    final private static String suppliers = "suppliers";
    final private static String ingredients = "ingredients";

    private MainForm f;
    private JPanel mainPanel;
    private JMenuBar Menu;
    private JMenu SubMenuSelect;
    private JMenu SubMenuInsert;
    private JMenuItem itemPizza;
    private JMenuItem itemOrders;
    private JMenuItem itemIngredients;
    private JMenuItem itemSuppliers;

    private JMenu SubMenuSelectJoin;
    private JMenuItem SelectJoin;

    private JMenuItem itemPizzaIns;
    private JMenuItem itemOrdersIns;
    private JMenuItem itemSuppliersIns;

    private JMenu SubMenuUpdate;
    private JMenuItem itemPizzaUpd;
    private JMenuItem itemOrdersUpd;
    private JMenuItem itemIngredUpd;
    private JMenuItem itemSuppUpd;

    private JMenu SubMenuDelete;
    private JMenuItem itemPizzaDel;
    private JMenuItem itemOrdersDel;
    private JMenuItem itemIngredDel;
    private JMenuItem itemSuppDel;

    private DefaultTableModel TableModel = null;
    private JScrollPane scrollPane = null;
    private JTable Table = null;

    public MainForm() {
        setBounds(300, 100, 1600, 900);
        Table = new JTable(TableModel){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Table.setAutoCreateRowSorter(true);
        Menu = new JMenuBar();

        SubMenuSelect = new JMenu("Вывод таблиц");
        itemPizza = new JMenuItem(pizza);
        SubMenuSelect.add(itemPizza);
        itemOrders = new JMenuItem(orders);
        SubMenuSelect.add(itemOrders);
        itemIngredients = new JMenuItem(ingredients);
        SubMenuSelect.add(itemIngredients);
        itemSuppliers = new JMenuItem(suppliers);
        SubMenuSelect.add(itemSuppliers);

        SubMenuSelectJoin = new JMenu("Объединение таблиц");
        SelectJoin = new JMenuItem("Select join");
        SubMenuSelectJoin.add(SelectJoin);
        SubMenuSelect.add(SubMenuSelectJoin);

        SubMenuInsert = new JMenu("Вставка в таблицы");
        itemPizzaIns = new JMenuItem(pizza);
        SubMenuInsert.add(itemPizzaIns);
        itemOrdersIns = new JMenuItem(orders);
        SubMenuInsert.add(itemOrdersIns);
        itemSuppliersIns = new JMenuItem(suppliers);
        SubMenuInsert.add(itemSuppliersIns);

        SubMenuUpdate = new JMenu("Обновление таблиц");
        itemPizzaUpd = new JMenuItem(pizza);
        SubMenuUpdate.add(itemPizzaUpd);
        itemOrdersUpd = new JMenuItem(orders);
        SubMenuUpdate.add(itemOrdersUpd);
        itemIngredUpd = new JMenuItem(ingredients);
        SubMenuUpdate.add(itemIngredUpd);
        itemSuppUpd = new JMenuItem(suppliers);
        SubMenuUpdate.add(itemSuppUpd);

        SubMenuDelete = new JMenu("Удаление таблиц");
        itemPizzaDel = new JMenuItem(pizza);
        SubMenuDelete.add(itemPizzaDel);
        itemOrdersDel = new JMenuItem(orders);
        SubMenuDelete.add(itemOrdersDel);
        itemIngredDel = new JMenuItem(ingredients);
        SubMenuDelete.add(itemIngredDel);
        itemSuppDel = new JMenuItem(suppliers);
        SubMenuDelete.add(itemSuppDel);

        Menu.add(SubMenuSelect);
        Menu.add(SubMenuInsert);
        Menu.add(SubMenuUpdate);
        Menu.add(SubMenuDelete);


        setJMenuBar(Menu);

        mainPanel = new JPanel();
        scrollPane = new JScrollPane(Table);
        scrollPane.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy (ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        Box contents = new Box(BoxLayout.Y_AXIS);
        contents.add(scrollPane);
        getContentPane().add(contents);

        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        itemPizza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[][] rs = null;
                String []header = null;
                Vector<String> head = new Vector<>();
                rs = Query.ReadAllQuery(pizza,head);

                header = new String[head.size()];
                for(int i =0; i < head.size(); i++){
                    header[i] = head.get(i);
                }
                TableModel = new DefaultTableModel(rs,header);
                Table.setModel(TableModel);
            }
        });
        itemOrders.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[][] rs = null;
                String []header = null;
                Vector<String> head = new Vector<>();
                rs = Query.ReadAllQuery(orders,head);

                header = new String[head.size()];
                for(int i =0; i < head.size(); i++){
                    header[i] = head.get(i);
                }
                TableModel = new DefaultTableModel(rs,header);
                Table.setModel(TableModel);
            }
        });
        itemIngredients.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[][] rs = null;
                String []header = null;
                Vector<String> head = new Vector<>();
                rs = Query.ReadAllQuery(ingredients,head);

                header = new String[head.size()];
                for(int i =0; i < head.size(); i++){
                    header[i] = head.get(i);
                }
                TableModel = new DefaultTableModel(rs,header);
                Table.setModel(TableModel);
            }
        });
        itemSuppliers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[][] rs = null;
                String []header = null;
                Vector<String> head = new Vector<>();
                rs = Query.ReadAllQuery(suppliers,head);

                header = new String[head.size()];
                for(int i =0; i < head.size(); i++){
                    header[i] = head.get(i);
                }
                TableModel = new DefaultTableModel(rs,header);
                Table.setModel(TableModel);
            }
        });
        SelectJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[][] rs = null;
                String []header = null;
                DialogClass dialog = new DialogClass(f,"join","fix");
                dialog.display();

                Vector<String> head = new Vector<>();
                ArrayList<String>listTable = dialog.getJoinTable();
                ArrayList<String>fields = dialog.getFields();
                rs = Query.SelectJoin(fields,listTable,head);
                header = new String[head.size()];
                for(int i =0; i < head.size(); i++){
                    header[i] = head.get(i);
                }
                TableModel = new DefaultTableModel(rs,header);
                Table.setModel(TableModel);
            }
        });
        itemPizzaIns.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogClass dialog = new DialogClass(f,"insert",pizza);
                dialog.display();
                ArrayList<String>fields = null;
                ArrayList<Integer>idIngred = null;
                fields = dialog.getFields();
                idIngred = dialog.getIdIngr();
                if(!fields.isEmpty() && idIngred != null){
                    Query.InsertPizzaQuery(fields.get(0),Integer.parseInt(fields.get(1)),Integer.parseInt(fields.get(2)),idIngred);
                }
            }
        });
        itemOrdersIns.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogInsOrders dialog = new DialogInsOrders(f,"insert");
                dialog.display();
                ArrayList<String>fields = null;
                ArrayList<Integer>idPizza = null;
                fields = dialog.getFields();
                idPizza = dialog.getIdIngr();
                if(!fields.isEmpty() && idPizza != null){
                    Query.InsertOrdersQuery(fields.get(0),fields.get(1),fields.get(2),fields.get(3),idPizza);
                }
            }
        });
        itemSuppliersIns.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogInsSuppliers dialog = new DialogInsSuppliers(f,"insert");
                dialog.display();
                ArrayList<String>fields = null;
                ArrayList<String>fieldsIngr = null;
                fields = dialog.getFields();
                fieldsIngr = dialog.getFieldsIngr();
                if(!fields.isEmpty() && fieldsIngr != null){
                    Query.InsertSuppliersQuery(fields.get(0),fields.get(1),fields.get(2),
                            fieldsIngr.get(0),
                            fieldsIngr.get(1));
                }
            }
        });
        itemPizzaUpd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogClass dialog = new DialogClass(f,"update",pizza);
                dialog.display();
                ArrayList<String>fields = null;
                fields =dialog.getFields();
                if(!fields.isEmpty()){
                    Query.UpdateQuery(pizza,fields.get(0),fields.get(1),fields.get(2),fields.get(3));
                    }
            }
        });
        itemOrdersUpd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogClass dialog = new DialogClass(f,"update",orders);
                dialog.display();
                ArrayList<String>fields = null;
                fields =dialog.getFields();
                if(!fields.isEmpty()){
                    Query.UpdateQuery(orders,fields.get(0),fields.get(1),fields.get(2),fields.get(3));
                }
            }
        });
        itemIngredUpd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogClass dialog = new DialogClass(f,"update",ingredients);
                dialog.display();
                ArrayList<String>fields = null;
                fields =dialog.getFields();
                if(!fields.isEmpty()){
                    Query.UpdateQuery(ingredients,fields.get(0),fields.get(1),fields.get(2),fields.get(3));
                }
            }
        });
        itemSuppUpd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogClass dialog = new DialogClass(f,"update",suppliers);
                dialog.display();
                ArrayList<String>fields = null;
                fields =dialog.getFields();
                if(!fields.isEmpty()){
                    Query.UpdateQuery(suppliers,fields.get(0),fields.get(1),fields.get(2),fields.get(3));
                }
            }
        });
        itemPizzaDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogClass dialog = new DialogClass(f,"delete",pizza);
                dialog.display();
                ArrayList<String>fields = null;
                fields =dialog.getFields();
                if(!fields.isEmpty()){
                    Query.DeleteQuery(pizza, fields.get(0),fields.get(1));
                }
            }
        });
        itemOrdersDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogClass dialog = new DialogClass(f,"delete",orders);
                dialog.display();
                ArrayList<String>fields = null;
                fields =dialog.getFields();
                if(!fields.isEmpty()){
                    Query.DeleteQuery(orders, fields.get(0),fields.get(1));
                }
            }
        });
        itemIngredDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogClass dialog = new DialogClass(f,"delete",ingredients);
                dialog.display();
                ArrayList<String>fields = null;
                fields =dialog.getFields();
                if(!fields.isEmpty()){
                    Query.DeleteQuery(ingredients, fields.get(0),fields.get(1));
                }
            }
        });
        itemSuppDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogClass dialog = new DialogClass(f,"delete",suppliers);
                dialog.display();
                ArrayList<String>fields = null;
                fields =dialog.getFields();
                if(!fields.isEmpty()){
                    Query.DeleteQuery(suppliers, fields.get(0),fields.get(1));
                }
            }
        });
    }
}
