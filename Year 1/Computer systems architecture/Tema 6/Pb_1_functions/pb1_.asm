;Read two numbers a and b (in base 10) from the keyboard and calculate their product. This value will be stored in a variable called "result" (defined in the data segment).
bits 32 


global start        


extern exit,printf,scanf               
import exit msvcrt.dll    
import printf msvcrt.dll    
import scanf msvcrt.dll    
    


segment data use32 class=data
    a dd 0
    b dd 0
    result dd 0
    read_message_a db "a=",0
    read_message_b db "b=",0
    read_format db "%d",0
    result_format db "%d*%d=%d",0

; our code starts here
segment code use32 class=code
    start:
        push dword read_message_a
        call [printf]
        add esp,4
        
        push dword a
        push dword read_format
        call [scanf]
        add esp,8
        
        push dword read_message_b
        call [printf]
        add esp,4
        
        push dword b
        push dword read_format
        call [scanf]
        add esp,8
        
        mov eax,[a]
        mov ebx,[b]
        imul ebx
        mov [result],eax
        
        push dword [result]
        push dword [b]
        push dword [a]
        push result_format
        call [printf]
        add esp,4*4
    
        
        push    dword 0      
        call    [exit]       
