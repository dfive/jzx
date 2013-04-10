package pt.ist.ave.jzx.Instructions;

public class DEC_HL extends Instruction {

	public DEC_HL(short opCode) {
		super(opCode);
	}

	@Override
	public void execute() {
//		dec16hl();
		_cpu.dec16hl();
		
	}

	@Override
	public int incTstates() {
//		m_tstates += 6;
		return 6;
	}

}
