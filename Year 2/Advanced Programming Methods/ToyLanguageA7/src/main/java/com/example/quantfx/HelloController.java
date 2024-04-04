package com.example.quantfx;
import com.example.quantfx.controller.GUIController;
import com.example.quantfx.model.statements.Statement;
import com.example.quantfx.model.values.IntValue;
import com.example.quantfx.model.values.Value;
import com.example.quantfx.program.*;
import com.example.quantfx.repository.Repository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.List;

public class HelloController
{
    // Sym table: variable name and value
    // Heap: address and value

    @FXML private ListView<String> programsListView;
    @FXML private TextField programStatesCountTextField;
    @FXML private ListView<String> outputListView;
    @FXML private ListView<String> fileTableListView;
    @FXML private ListView<String> programStatesListView;
    @FXML private ListView<String> executionStackListView;
    @FXML private TableView<SymbolTableKeyValuePair> symbolTableView;
    @FXML private TableView<HeapKeyValuePair> heapTableView;
    @FXML private Button runOneStepButton;
    @FXML private Button loadExampleButton;

    Interpreter _interpreter;
    GUIController _controller;

    @FXML
    private void initialize()
    {
        _interpreter = new Interpreter();
        _interpreter.initialize();
        _controller = new GUIController();

        for (Interpreter.ExampleData example : _interpreter.examples)
        {
            programsListView.getItems().add(example.text);
        }
    }

    @FXML
    public void onProgramSelected()
    {
        refreshController();
        displayProgramState(_controller.getProgramStateByID(getSelectedProgramId()));
    }

    private int getSelectedProgramId()
    {
        return Integer.parseInt(programStatesListView.getSelectionModel().getSelectedItem());
    }

    private int getSelectedProgramStateIndex()
    {
        return programStatesListView.getSelectionModel().getSelectedIndex();
    }

    @FXML
    protected void onRunOneStepButtonClick()
    {
        if (getSelectedProgramStateIndex() < 0)
        {
            return;
        }

        ProgramState currentProgramState = _controller.getProgramStates().get(getSelectedProgramStateIndex());
        try
        {
            _controller.oneStep();
        }
        catch (Exception exception)
        {
            System.out.println(exception.getMessage());
        }

        refreshController();
        displayProgramState(currentProgramState);
    }

    @FXML
    protected void onLoadExampleButtonClick()
    {
        int index = programsListView.getSelectionModel().getSelectedIndex();

        Statement statement = _interpreter.examples.get(index).statement;
        ProgramState programState = new ProgramState(statement); // Creates a new program state with the given statement
        Repository repository = new Repository(programState, "log" + index + ".txt");

        try
        {
            _controller.setNewRepo(repository);
        }
        catch (Exception exception)
        {
            System.out.println(exception.getMessage());
        }

        refreshController();
    }

    private void clearAll()
    {
        heapTableView.getItems().clear();
        symbolTableView.getItems().clear();
        outputListView.getItems().clear();
        fileTableListView.getItems().clear();
        programStatesListView.getItems().clear();
        executionStackListView.getItems().clear();
    }

    private void clearOutput()
    {
        outputListView.getItems().clear();
    }

    private void refreshController()
    {
        int index = getSelectedProgramStateIndex();
        clearAll();
        List<String> programStates = _controller.getProgramIDs();
        ObservableList<String> observableList = FXCollections.observableArrayList(programStates);
        // Display the id of each program state
        programStatesListView.setItems(observableList);

        // Display the number of program states
        programStatesCountTextField.setText("Count: " + _controller.getProgramsCount());
        programStatesListView.getSelectionModel().select(index);
    }

    private void displayProgramState(ProgramState state)
    {
        // Display the execution stack
        executionStackListView.getItems().clear();
        executionStackListView.getItems().addAll(state.getStackInstruction());

        // Display the output
        outputListView.getItems().clear();
        outputListView.getItems().add(state.getOutput().toString());

        // Display the file table
        fileTableListView.getItems().clear();
        fileTableListView.getItems().addAll(state.getFileTable().toString());

        // Filling tables
        loadSymbolTable(state);
        loadHeapTable(state);

    }


    private void loadSymbolTable(ProgramState state)
    {
        Dictionary<String, Value> symbolTable = state.getSymbolTable();

        symbolTableView.getItems().clear();
        TableColumn keyColumn = new TableColumn("Key");
        TableColumn valueColumn = new TableColumn("Value");

        keyColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        symbolTableView.getColumns().setAll(keyColumn, valueColumn);

        ObservableList<SymbolTableKeyValuePair> data = FXCollections.observableArrayList();

        for (String key : symbolTable.getKeys())
        {
            data.add(new SymbolTableKeyValuePair(key, symbolTable.get(key)));
        }

        symbolTableView.setItems(data);
    }

    private void loadHeapTable(ProgramState state)
    {
        Heap<Integer, Value> heap = state.getHeap();

        heapTableView.getItems().clear();
        TableColumn keyColumn = new TableColumn("Key");
        TableColumn valueColumn = new TableColumn("Value");

        keyColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

        heapTableView.getColumns().setAll(keyColumn, valueColumn);

        ObservableList<HeapKeyValuePair> data = FXCollections.observableArrayList();

        for (Integer key : heap.getKeys())
        {
            data.add(new HeapKeyValuePair(key, heap.get(key)));
        }

        heapTableView.setItems(data);
    }

    public static class HeapKeyValuePair
    {
        private final Integer key;
        private final Value value;

        public HeapKeyValuePair(Integer key, Value definition)
        {
            this.key = key;
            this.value = definition;
        }

        public Integer getKey()
        {
            return key;
        }

        public Value getValue()
        {
            return value;
        }
    }

    public static class SymbolTableKeyValuePair
    {
        private final String key;
        private final Value value;

        public SymbolTableKeyValuePair(String key, Value definition)
        {
            this.key = key;
            this.value = definition;
        }

        public String getKey()
        {
            return key;
        }

        public Value getValue()
        {
            return value;
        }
    }

    public static class CyclicBarrierTableWrapper
    {
        private int index;
        private Value value;
        private List<Integer> list;

        public CyclicBarrierTableWrapper(int index, Value value, List<Integer> list)
        {
            this.index = index;
            this.value = value;
            this.list = list;
        }

        public int getIndex()
        {
            return index;
        }

        public int getValue()
        {
            return ((IntValue)value).getValue();
        }

        public List<Integer> getList()
        {
            return list;
        }
    }
}