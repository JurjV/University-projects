package controller;

import adt.statements.IStmt;
import adt.structures.MyIStack;
import adt.structures.PrgState;
import adt.values.RefValue;
import adt.values.Value;
import repository.IRepository;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repo;

    public Controller(IRepository repo) {
        this.repo = repo;
    }

    public PrgState oneStep(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getExeStack();
        if (stk.isEmpty()) throw new MyException("Program state stack is empty.");
        IStmt crtStmt = stk.pop();
        state.setExeStack(stk);
        return crtStmt.execute(state);
    }

    Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(e -> symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void allStep(int flag) throws MyException, IOException {
        PrgState prg = repo.getCrtPrg();
        repo.logPrgStateExec();
        if (flag == 1)
            System.out.println(prg);
        //here you can display the prg state
        while (!prg.getExeStack().isEmpty()) {
            oneStep(prg);
            repo.logPrgStateExec();
            prg.getHeap().setContent((HashMap<Integer, Value>) unsafeGarbageCollector(getAddrFromSymTable(prg.getSymTable().getContent().values(), prg.getHeap().getContent()), prg.getHeap().getContent()));
            if (flag == 1)
                System.out.println(prg);
        }
        if (flag == 0)
            System.out.println(prg.getOut());
    }

    List<Integer> getAddrFromSymTable(Collection<Value> symTableValues, Map<Integer,Value> heap){
        List<RefValue> refValues = symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> (RefValue)v)
                .toList();

        List<Integer> addresses = new LinkedList<>();
        for (RefValue r : refValues)
        {
            int addr = r.getAddress();
            if (!addresses.contains(addr))
                addresses.add(addr);

            Value loop = heap.get(addr);
            while (loop != null && loop.equals(new RefValue()))
            {
                addr = ((RefValue)loop).getAddress();
                loop = heap.get(addr);

                if (!addresses.contains(addr))
                    addresses.add(addr);
            }
        }

        return addresses;
    }
}
