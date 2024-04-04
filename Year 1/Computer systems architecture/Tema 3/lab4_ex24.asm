bits 32 

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ;Given the doubleword M, compute the doubleword MNew as follows:
    ;the bits 0-3 a of MNew are the same as the bits 5-8 a of M.
    ;the bits 4-7 a of MNew have the value 1
    ;the bits 27-31 a of MNew have the value 0
    ;the bits 8-26 of MNew are the same as the bits 8-26 a of M.
    
    
    m dd 11001100110101010101001010111001b
    mnew dd 0

; our code starts here
segment code use32 class=code
    start:
        mov ebx,0 ; the result will be on ebx
        
        mov eax,[m]
        and eax,00000000000000000000000111100000b ; we isolate the bits 5-8 of M
        mov cl,5
        ror eax,cl ; we rotate 5 positions to the right
        or ebx,eax ; we move the bits to the result
        
        or ebx,00000000000000000000000011110000b ; we  put  the value 1 on bits 4-7 of MNew
        
        and ebx,00000111111111111111111111111111b ; we put the value 0 on bits 27-31 of MNew
        
        mov eax,[m]
        and eax,00000111111111111111111100000000b ; we isolate the bits 8-26 of M
        or ebx,eax  ; we move the bits 8-26 of M on the bits 8-26 of MNew
        
        mov [mnew],ebx ; we store the result on MNew
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
