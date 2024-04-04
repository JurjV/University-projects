package repository;

import adt.structures.PrgState;
import controller.MyException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;

public class Repository implements IRepository {

    List<PrgState> repo;
    private final String filePath;

    public Repository(PrgState prgState, String path) {
        this.repo = new LinkedList<PrgState>();
        this.filePath = path;
        this.add(prgState);
    }

    @Override
    public void add(PrgState e) {
        repo.add(e);
    }

    @Override
    public PrgState getCrtPrg() {
        return repo.get(0);
    }

    @Override
    public void logPrgStateExec() throws MyException, IOException {
        PrintWriter file;
        file = new PrintWriter(new BufferedWriter(new FileWriter(filePath,true)));
        file.println(this.repo.get(0).programStateTOString());
        file.close();
    }

    @Override
    public String toString() {
        return "Repository{" +
                "repo=" + repo +
                '}';
    }
}
