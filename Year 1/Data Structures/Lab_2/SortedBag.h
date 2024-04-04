#pragma once
//DO NOT INCLUDE SORTEDBAGITERATOR

//DO NOT CHANGE THIS PART
typedef int TComp;
typedef TComp TElem;
typedef bool(*Relation)(TComp, TComp);
#define NULL_TCOMP -11111;

class SortedBagIterator;

class Node {
public:
    TComp element;
    int frequency;
    Node* prev;
    Node* next;

    // Constructor
    Node(const TComp& e, int freq) : element(e), frequency(freq), prev(nullptr), next(nullptr) {}
};

class SortedBag {
	friend class SortedBagIterator;
    friend class Node;

private:
    Relation r;
    Node *head;
    int nr_elems;

public:
	//constructor
	explicit SortedBag(Relation r);

	//adds an element to the sorted bag
	void add(TComp e);

	//removes one occurence of an element from a sorted bag
	//returns true if an eleent was removed, false otherwise (if e was not part of the sorted bag)
	bool remove(TComp e);

	//checks if an element appears in the sorted bag
	bool search(TComp e) const;

	//returns the number of occurrences for an element in the sorted bag
	int nrOccurrences(TComp e) const;

	//returns the number of elements from the sorted bag
	int size() const;

	//returns an iterator for this sorted bag
	SortedBagIterator iterator() const;

	//checks if the sorted bag is empty
	bool isEmpty() const;

	//destructor
	~SortedBag();

    int removeAll();
};