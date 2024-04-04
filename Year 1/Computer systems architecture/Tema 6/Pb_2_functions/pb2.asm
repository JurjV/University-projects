;Read two numbers a and b (in base 10) from the keyboard and calculate a/b. This value will be stored in a variable called "result" (defined in the data segment). The values are considered in signed representation.
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,printf,scanf          ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll
import printf msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import scanf msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dd 0
    b dd 0
    result dd 0
    read_text_a dd "a=",0
    read_text_b dd "b=",0
    read_format dd "%d",0
    result_format dd "%d/%d=%d",0
    
    

; our code starts here
segment code use32 class=code
    start:
        push dword read_text_a
        call [printf]
        add esp,4
        
        push dword a
        push dword read_format
        call [scanf]
        add esp,8
        
        push dword read_text_b
        call [printf]
        add esp,4
        
        push dword b
        push dword read_format
        call [scanf]
        add esp,8
        
        mov eax,[a]
        cdq
        mov ebx,[b]
        idiv ebx
        mov  [result],eax
        
        push dword [result]
        push dword [b]
        push dword [a]
        push dword result_format
        call [printf]
        add esp,4*4
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
