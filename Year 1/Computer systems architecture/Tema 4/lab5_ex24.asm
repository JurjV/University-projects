bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    
    ;Two byte strings A and B are given. Obtain the string R by concatenating the elements of B in reverse order and the elements of A in reverse order.
    
    a db 1,2,3,4,5
    la equ $-a
    b db 6,7,8,9,10
    lb equ $-b,
    ld equ la+lb
    d times ld db 0 ; rezerves ld bytes for concatenating the two strings

; our code starts here
segment code use32 class=code
    start:
        
        mov ecx,ld ; initialise ecx with ld and esi with 0
        mov esi,0
        jecxz Final   ; if there are no elements on the string we end the program
        Concatenation:
            mov al,[a+ecx-1]   ; we compute the list a+b from the end
            mov [d+esi],al     ; we mov the current element in d
            inc esi
        loop Concatenation
        Final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
