;A file name is given (defined in the data segment). Create a file with the given name, then read numbers from the keyboard and write those numbers in the file, until the value '0' is read from the keyboard.
bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit,fopen,fclose,fprintf,scanf               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fopen msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fclose msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import fprintf msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
import scanf msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    file_name db "file.txt",0
    read_format db "%d",0
    file_descriptor dd 0
    write_mode db "w",0
    write_format db "%d",0ah,0
    a dd 0

; our code starts here
segment code use32 class=code
    start:
        push dword write_mode
        push dword file_name
        call [fopen]
        add esp,4*2
        
        mov [file_descriptor],eax
        cmp eax,0
        je Gata
        Reading_loop:
            push dword a
            push dword read_format
            call [scanf]
            add esp,4*2
            
            cmp [a],dword 0
            je Gata
            push dword [a]
            push dword write_format
            push dword [file_descriptor]
            call [fprintf]
            add esp,4*3
            jmp Reading_loop
        Gata:
        
        push dword [file_descriptor]
        call [fclose]    ; closing the file
        add esp, 4
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
