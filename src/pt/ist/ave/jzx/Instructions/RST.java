package pt.ist.ave.jzx.Instructions;


public class RST extends Instruction {

	public RST(short opCode) {
		super(opCode);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
//		push(m_pc16);
//		m_pc16 = 0x38;
		_cpu.push(_cpu.getM_pc16());
		_cpu.setM_pc16(0x38);
	}

	@Override
	public int incTstates() {
		// TODO Auto-generated method stub
//		m_tstates += 11;
		return 11;
	}

}
