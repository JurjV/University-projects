#include "SMMIterator.h"
#include "SortedMultiMap.h"
#include <vector>
#include <exception>

using namespace std;

SortedMultiMap::SortedMultiMap(Relation r) {
    this->r = r;
    this->nr_elems = 0;
    this->head = 0;
    //Theta(1) complexity
}

void SortedMultiMap::add(TKey c, TValue v) {
    this->nr_elems++;
    if (this->isEmpty()) {
        Node new_node(c, v, -1, -1);
        this->all_elems.push_back(new_node);
        return;
    } else {
        for (auto &value: all_elems) {
            if (value.key == c) {
                value.values.push_back(v);
                return;
            }
        }
        Node new_node(c, v, -1, -1);
        this->all_elems.push_back(new_node);
        for (int i = all_elems.size() - 1; i > 0; --i) {
            if (r(all_elems[i].key, all_elems[i - 1].key)) {
                std::swap(all_elems[i], all_elems[i - 1]);
            }
        }
        all_elems[head].next = 1;
        all_elems[head].prev = -1;
        int i;
        for (i = 1; i < all_elems.size(); i++) {
            all_elems[i].prev = i - 1;
            if (i != all_elems.size() - 1) {
                all_elems[i].next = i + 1;
            }
        }
    }
    //Best case happens when the key is located in the
    //head of the DLLA,in this case the complexity is
    //Theta(1)
    //Worst case happens when the key is not located in the
    //DLLA and it needs to be put on the first position,
    //based on the relation,in this case the complexity is
    //O(n)

    ///overall complexity is O(n)
}


vector<TValue> SortedMultiMap::search(TKey c) const {
    vector<TValue> values;
    for (const auto &value: all_elems) {
        if (value.key == c) {
            for (auto v: value.values) {
                values.push_back(v);
            }
        }
    }
    return values;
    //Best case happens when the key is in the head of the DLLA,
    //in this case the complexity is
    //Theta(v),where v is the number of values of the first key
    //worst case happens when the key is on the last position of
    //the DLLA,in this case the complexity is Theta(max(n+v)),
    //where n is the number of keys

    ///so the overall complexity is O(max(n+v))
}

bool SortedMultiMap::remove(TKey c, TValue v) {
    int j = 0;
    for (auto &node: all_elems) {
        if (node.key == c) {
            if (node.values.size() != 1) {
                int i = 0;
                for (auto &value: node.values) {
                    if (value == v) {
                        node.values.erase(node.values.begin() + i);
                        nr_elems--;
                        return true;
                    }
                    i++;
                }
            } else {
                if (node.values[0] == v) {
                    all_elems.erase(all_elems.begin() + j);
                    nr_elems--;
                    if (!this->isEmpty()) {
                        all_elems[head].next = 1;
                        all_elems[head].prev = -1;
                        int i;
                        for (i = 1; i < all_elems.size(); i++) {
                            all_elems[i].prev = i - 1;
                            if (i != all_elems.size() - 1) {
                                all_elems[i].next = i + 1;
                            }
                        }
                    }
                    return true;
                } else
                    return false;
            }
        }
        j++;
    }
    return false;
    //Best case happens when the key is in the head of
    //the DLLA and the value is in the head of the values vector
    //in this case the complexity is Theta(1)
    //Worst case happens when the key is in the tail of the
    //DLLA and has only one pair,in this case we need to erase the node completely
    //and update the next and prev of every node
    //complexity O(n)

    ///Overall complexity is O(n)
}

int SortedMultiMap::addIfNotPresent(SortedMultiMap &smm) {
    if (!smm.isEmpty()) {
        int added_pairs = 0;
        for (auto &node: smm.all_elems) {
            int ok = 0;
            for (auto &node1: this->all_elems) {
                if (node.key == node1.key) {
                    ok = 1;
                    break;
                }
            }
            if (ok != 1) {
                all_elems.push_back(node);
                nr_elems = nr_elems + node.values.size();
                added_pairs = added_pairs + node.values.size();
                for (int i = this->all_elems.size() - 1; i > 0; --i) {
                    if (r(this->all_elems[i].key, this->all_elems[i - 1].key)) {
                        std::swap(this->all_elems[i], this->all_elems[i - 1]);
                    }
                }
                this->all_elems[head].next = 1;
                this->all_elems[head].prev = -1;
                int i;
                for (i = 1; i < this->all_elems.size(); i++) {
                    this->all_elems[i].prev = i - 1;
                    if (i != this->all_elems.size() - 1) {
                        this->all_elems[i].next = i + 1;
                    }
                }
            }
        }
        return added_pairs;
    }
    return 0;
    //Best case happens when all of the keys match between the two sorted multi-maps
    //the complexity is O(n^2)
    //Worst case happens when none of the keys from the given SortedMultiMap
    //are in the SortedMultiMap and by the relation,all of the nodes should be placed in the head
    //of the DLLA,in this case the complexity is O(n^2)

    ///Overall complexity is O(n^2)
}


int SortedMultiMap::size() const {
    return nr_elems;
    //Theta(1) complexity
}

bool SortedMultiMap::isEmpty() const {
    if (this->all_elems.empty())
        return true;
    return false;
    //Theta(1) complexity
}

SMMIterator SortedMultiMap::iterator() const {
    return SMMIterator(*this);
}

SortedMultiMap::~SortedMultiMap() = default;
