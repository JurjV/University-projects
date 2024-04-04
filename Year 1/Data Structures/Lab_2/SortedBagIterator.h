#pragma once
#include "SortedBag.h"

class SortedBag;

class SortedBagIterator
{
	friend class SortedBag;
    friend class Node;

private:
	const SortedBag& bag;
	SortedBagIterator(const SortedBag& b);
    Node *current;
    int count;


public:
	TComp getCurrent();
	bool valid();
	void next();
	void first();
};

