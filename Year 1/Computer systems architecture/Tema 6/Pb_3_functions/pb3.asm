;Two natural numbers a and b (a, b: dword, defined in the data segment) are given. Calculate their sum and display the result in the following format: "<a> + <b> = <result>". Example: "1 + 2 = 3"
;The values will be displayed in decimal format (base 10) with sign.
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,scanf,printf         ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
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
    result_format dd "%d + %d = %d",0
    

; our code starts here
segment code use32 class=code
    start:
        push dword read_text_a
        call [printf]
        add esp,4
        
        push dword a
        push dword read_format
        call [scanf]
        add esp,4*2
        
        push dword read_text_a
        call [printf]
        add esp,4
        
        push dword b
        push dword read_format
        call [scanf]
        add esp,4*2
        
        mov eax,[a]
        add eax,[b]
        mov [result],eax
        
        push dword [result]
        push dword [b]
        push dword [a]
        push dword result_format
        call [printf]
        add esp,4*4
        
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
