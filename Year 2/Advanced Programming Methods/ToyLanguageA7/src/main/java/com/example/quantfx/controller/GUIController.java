package com.example.quantfx.controller;

import com.example.quantfx.model.values.RefValue;
import com.example.quantfx.model.values.Value;
import com.example.quantfx.program.Heap;
import com.example.quantfx.program.ProgramState;
import com.example.quantfx.repository.Repository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class GUIController
{
    Repository repo;
    boolean showSteps = true;
    private ExecutorService executor;
    private List<ProgramState> programsList = new ArrayList<>();
    private ProgramState lastProgramState;
    private boolean running;

    public GUIController()
    {
        executor = Executors.newFixedThreadPool(2);
    }

    public void setNewRepo(Repository repository) throws Exception {
        repo = repository;

        executor = Executors.newFixedThreadPool(2);
        programsList = removeCompletedPrograms(repo.getPrograms());
        running = true;
    }

    public void oneStep() throws Exception {
        if (!running) return;

        System.out.println(programsList.size());
        oneStepForAllPrograms(programsList);
        programsList = repo.getPrograms();
        lastProgramState = programsList.get(0);
        programsList = removeCompletedPrograms(programsList);

        if (programsList.isEmpty())
        {
            executor.shutdownNow();
            repo.setPrograms(programsList);
            running = false;
        }
        else lastProgramState = programsList.get(0);
    }

    public void oneStepForAllPrograms(List<ProgramState> programs) throws Exception
    {
        // Print the program states
        programs.forEach(program -> repo.logExecution(program));

        // Run concurrently one step for each program
        List<Callable<ProgramState>> callList = programs.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(p::oneStep))
                .toList(); // collect(Collectors.toList()) alternative

        List<ProgramState> newProgramsList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try
                    {
                        return future.get();
                    }
                    catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                    }
                    return null;
                })
                .filter(p -> p != null)
                .collect(Collectors.toList());

        programs.addAll(newProgramsList);

        safeGarbageCollector(programs);

        // Print the program states
        programs.forEach(program -> repo.logExecution(program));

        // Save the current programs in the repository
        repo.setPrograms(programs);
    }

    public int getProgramsCount()
    {
        return programsList.size();
    }
    public List<ProgramState> getProgramStates()
    {
        return repo.getPrograms();
    }
    public List<String> getProgramIDs() {
        List<String> programs = new LinkedList<>();
        for (ProgramState prg : programsList){
            programs.add(String.valueOf(prg.getId()));
        }
        return programs;
    }

    public ProgramState getLastProgramState()
    {
        return lastProgramState;
    }

    public ProgramState getProgramStateByID(int id)
    {
        for (ProgramState prg : programsList)
            if (prg.getId() == id)
                return prg;
        return null;
    }

    void safeGarbageCollector(List<ProgramState> prgList)
    {
        Map<Integer, Value> newHeap = new HashMap<>();

        for (ProgramState prg : prgList) {
            Map<Integer, Value> prgHeap = unsafeGarbageCollector(
                    getAddressesFromSymbolTable(prg.getSymbolTable().getContent().values(),
                            prg.getHeap().getContent()),
                    prg.getHeap().getContent());

            newHeap.putAll(prgHeap);
        }

        for (ProgramState prg : prgList) {
            prg.getHeap().setContent(newHeap);
        }
    }

    HashMap<Integer, Value> unsafeGarbageCollector(List<Integer> symTable, HashMap<Integer, Value> heap)
    {
        return heap.entrySet().stream()
                .filter(e -> symTable.contains(e.getKey()))
                .collect(Collectors.toMap(HashMap.Entry::getKey, HashMap.Entry::getValue, (e1, e2) -> e2, HashMap::new));
    }


    List<Integer> getAddressesFromSymbolTable(Collection<Value> symTableValues, Map<Integer,Value> heap){
        List<RefValue> refValues = symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> (RefValue)v)
                .toList();

        List<Integer> addresses = new LinkedList<>();
        for (RefValue r : refValues)
        {
            int addr = r.address;
            if (!addresses.contains(addr))
                addresses.add(addr);

            Value loop = heap.get(addr);
            while (loop != null && loop.equals(new RefValue()))
            {
                addr = ((RefValue)loop).address;
                loop = heap.get(addr);

                if (!addresses.contains(addr))
                    addresses.add(addr);
            }
        }

        return addresses;
    }

    Map<Integer,Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer,Value> heap){
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    public List<ProgramState> removeCompletedPrograms(List<ProgramState> programList)
    {
        return programList.stream()
                .filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }
}
