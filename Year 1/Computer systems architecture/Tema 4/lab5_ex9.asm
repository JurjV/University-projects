bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    
    ; A byte string S of length l is given. Obtain the string D of length l-1 so that the elements of ; D represent the difference between every two consecutive elements of S.
    
    s db 1,2,4,6,8,8,9
    l equ $-s
    d times l-1 db 0 ; rezerve l-1 bytes for the difference of the numbers and initialize them with 0

; our code starts here
segment code use32 class=code
    start:
        
        mov ecx,l-1 ; initialise ecx with l-1
        mov esi,0
        jecxz Final
        Difference:
            mov al,[s+esi]
            mov bl,[s+esi+1]
            sub bl,al ; compute the difference between the two consecutive numbers
            mov [d+esi],bl ; mov it in the result
            inc esi ; increment the position
        loop Difference
        Final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
